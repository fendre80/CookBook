package hu.bme.aut.android.cookbook.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cookbook.R

class MyListAdapter(private var isIngredient: Boolean) :
    RecyclerView.Adapter<MyListAdapter.MyListViewHolder>() {

    private var items = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        val itemView : View
        if (isIngredient) {
            itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.details_ingredient, parent, false)
        }
        else {
            itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.details_description, parent, false)

        }
        return MyListViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        val item = items[position]
        holder.tvList.text = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(newItems : List<String>) {
        items.clear()
        if (!isIngredient) {
            for (item in newItems) {
                items.add((newItems.indexOf(item) + 1).toString() + ". " + item)
            }
        }
        else {
            items.addAll(newItems)
        }
        Log.i("myitems", items.toString())
            notifyDataSetChanged()
    }


    inner class MyListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        val tvList : TextView

        init {
            if (isIngredient)
                tvList = listView.findViewById(R.id.tvIngredient_details)
            else
                tvList = listView.findViewById(R.id.tvDescription_details)
        }
    }
}