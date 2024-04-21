package ru.trifonov.clubfirst.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.data.dto.Tag

class TagsAdapter(

    private val tagsList: List<Tag>,
) : RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TagViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.tag_item,
            parent, false
        )
        return TagViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.title.text = tagsList[position].name
    }

    override fun getItemCount(): Int {
        return tagsList.size
    }

    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
    }
}