package ru.trifonov.clubfirst.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.data.Meeting

class ListMeetingAdapter(
    val items : ArrayList<Meeting>,
    val context: Context,
    private val navController: NavController
) : RecyclerView.Adapter<ListMeetingAdapter.PlaceHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.name_chat_user, parent,false)
        return PlaceHolder(view)
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {

        holder.text.text = items[position].name
        holder.card.setOnClickListener {
            navController.navigate(R.id.action_chat_to_dialog, Bundle().also { it.putString("id", items[position].name) })
        }
    }

    class PlaceHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val text: TextView
        val card : CardView
        init {
            text = itemView.findViewById(R.id.name_chat_user)
            card = itemView.findViewById(R.id.card_user)
        }
    }
}