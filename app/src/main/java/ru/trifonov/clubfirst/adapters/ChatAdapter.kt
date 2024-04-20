package ru.trifonov.clubfirst.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ru.trifonov.clubfirst.R

import ru.trifonov.clubfirst.databinding.NameChatUserBinding

class ChatAdapter(
    val items : ArrayList<String>,
    val context: Context,
    private val navController: NavController) : RecyclerView.Adapter<ChatAdapter.PlaceHolder>() {
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

        holder.text.text = items[position]
        holder.card.setOnClickListener {
            navController.navigate(R.id.action_chat_to_dialog, Bundle().also { it.putString("id", items[position]) })
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



    /*
    @SuppressLint("NotifyDataSetChanged")
    fun createAll(partnerList: MutableList<Place>){
        deleter()
        val partnerList2 = mutableListOf<Place>()
        partnerList.forEach {
            partnerList2.add(it)
        }
        PlaceList = partnerList2 as ArrayList<Place>
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleter(){
        PlaceList.removeAll(PlaceList.toSet())
    }
    */

