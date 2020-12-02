package hu.bme.aut.android.cookbook.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import hu.bme.aut.android.cookbook.R

class IngredientAdapter(private var context: Context) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    private var items = mutableListOf<String>()

    init {
        items.add(String())
    }


    inner class IngredientViewHolder(ingredientView: View) : RecyclerView.ViewHolder(ingredientView) {
        val txtInputEditText : TextInputEditText
        val plusButton : ImageButton
        val removeButton : ImageButton

        init {
            txtInputEditText = ingredientView.findViewById(R.id.txtInpEdTxt)
            plusButton = ingredientView.findViewById(R.id.imgbtn_plus)
            removeButton = ingredientView.findViewById(R.id.imgbtn_minus)
            removeButton.isGone = true
            plusButton.isGone = false

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
            .from(context)
            .inflate(R.layout.ingredient_row, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = items[position]
        holder.plusButton.setImageResource(R.drawable.ic_plus)
        holder.removeButton.setImageResource(R.drawable.ic_minus)
        holder.txtInputEditText.setText(item)
    }


    override fun getItemCount(): Int {
        return items.size
    }
}