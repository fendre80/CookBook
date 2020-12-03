package hu.bme.aut.android.cookbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import hu.bme.aut.android.cookbook.R

class IngredientAdapter() :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    private var items = ArrayList<String>()
    private var newItems = ArrayList<String>()

    init {
        items.add(String())
    }

    fun getItems(): ArrayList<String> {
        return items.filter { it.isNotEmpty() } as ArrayList<String>
    }

    fun setItems(newItems: ArrayList<String>) {
        items = newItems
        this.newItems = newItems
    }


    inner class IngredientViewHolder(ingredientView: View) :
        RecyclerView.ViewHolder(ingredientView) {
        val txtInputEditText: TextInputEditText = ingredientView.findViewById(R.id.txtInpEdTxt)
        val plusButton: ImageButton = ingredientView.findViewById(R.id.imgbtn_plus)
        val removeButton: ImageButton = ingredientView.findViewById(R.id.imgbtn_minus)

        init {
            removeButton.isGone = true
            plusButton.isGone = false

            txtInputEditText.doAfterTextChanged {
                items[adapterPosition] = it.toString()
            }

            removeButton.setOnClickListener {
                items.removeAt(adapterPosition)
                removeButton.isGone = true
                plusButton.isGone = false
                notifyItemRemoved(adapterPosition)
            }
            plusButton.setOnClickListener {
                items.add(String())
                removeButton.isGone = false
                plusButton.isGone = true
                notifyItemInserted(items.size - 1)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.ingredient_row, parent, false)
        if (itemCount > 1)
            itemView.requestFocus()
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientAdapter.IngredientViewHolder, position: Int) {
        val item = items[position]
        holder.plusButton.setImageResource(R.drawable.ic_plus)
        holder.removeButton.setImageResource(R.drawable.ic_minus)
        holder.txtInputEditText.setText(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}