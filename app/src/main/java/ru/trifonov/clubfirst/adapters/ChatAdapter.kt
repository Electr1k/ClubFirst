package ru.trifonov.clubfirst.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.databinding.MessageBinding

class ChatAdapter(val listener: Listener, val context: Context): RecyclerView.Adapter<ChatAdapter.PlaceHolder>() {
    private var MessageList=ArrayList<String>()

    class PlaceHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = MessageBinding.bind(item)
        @RequiresApi(Build.VERSION_CODES.Q)
        fun bind(message: String, listener: Listener, context: Context) = with(binding){
            textMessage.text = message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.message,parent,false)
        return  PlaceHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.bind(MessageList[position], listener, context)
    }

    override fun getItemCount(): Int {
        return MessageList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun createElement(mes : String){
        MessageList.add(mes)
        notifyDataSetChanged()
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
    interface Listener{
        fun onClick()
    }
}