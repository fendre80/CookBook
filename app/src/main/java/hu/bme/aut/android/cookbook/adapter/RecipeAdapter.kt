package hu.bme.aut.android.cookbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cookbook.R
import hu.bme.aut.android.cookbook.data.RecipeItem

class RecipeAdapter(private val listener: RecipeItemClickListener) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(), Filterable {
    private var items = mutableListOf<RecipeItem>()
    private var filteredItems = ArrayList<RecipeItem>(items.size)

    init {
        filteredItems = items as ArrayList<RecipeItem>
    }


    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView

        var item: RecipeItem? = null

        init {
            nameTextView = itemView.findViewById(R.id.tvRecipe_name_item)
            nameTextView.setOnClickListener {
                item?.let { it1 -> listener.onItemClicked(it1) }
            }
        }
    }

    fun updateRecipeItem(itemList: List<RecipeItem>) {
        items.clear()
        items.addAll(itemList)
        notifyDataSetChanged()
    }

    interface RecipeItemClickListener {
        fun onItemClicked(item: RecipeItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = filteredItems[position]
        holder.nameTextView.text = item.name

        holder.item = item
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredItems = items as ArrayList<RecipeItem>
                } else {
                    val resultList = ArrayList<RecipeItem>()
                    for (row in items) {
                        if (row.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    filteredItems = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredItems
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as ArrayList<RecipeItem>
                notifyDataSetChanged()
            }

        }
    }

}