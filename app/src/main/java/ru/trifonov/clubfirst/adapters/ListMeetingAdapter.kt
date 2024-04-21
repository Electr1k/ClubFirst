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
    var items : ArrayList<Meeting>,
    val context: Context,
    private val navController: NavController
) : RecyclerView.Adapter<ListMeetingAdapter.PlaceHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meeting, parent,false)
        return PlaceHolder(view)
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {

        holder.text_name_user.text = "Встреча: " + items[position].name_user
        holder.text_time_meet.text = "Встреча запланирована на " + items[position].time
    }

    class PlaceHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val text_name_user: TextView
        val text_time_meet : TextView
        init {
            text_name_user = itemView.findViewById(R.id.meet)
            text_time_meet = itemView.findViewById(R.id.meet_time)
        }
    }

    fun updateList(mas : ArrayList<Meeting>){
        items = mas
        notifyDataSetChanged()
    }
}