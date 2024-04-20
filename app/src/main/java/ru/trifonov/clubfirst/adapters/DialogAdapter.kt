package ru.trifonov.clubfirst.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.data.MessageItem
import ru.trifonov.clubfirst.databinding.MessageUser1Binding
import ru.trifonov.clubfirst.databinding.MessageUserBinding

class DialogAdapter(val listener: Listener, val context: Context): RecyclerView.Adapter<DialogAdapter.PlaceHolder>() {
    private var MessageList=ArrayList<MessageItem>()
    class PlaceHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = MessageUser1Binding.bind(item)
        @RequiresApi(Build.VERSION_CODES.Q)
        fun bind(item: MessageItem, listener: Listener, context: Context) = with(binding){
                textMessgae.text = item.mes
                textMessgaeUser.text = item.mes
                if (!item.type){
                    cardUserMes.visibility = View.GONE
                }
                else {
                    cardGuestMes.visibility = View.GONE
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.message_user1, parent, false)
            return PlaceHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.bind(MessageList[position], listener, context)
    }

    override fun getItemCount(): Int {
        return MessageList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun createElement(mes : MessageItem){
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