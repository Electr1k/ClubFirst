package ru.trifonov.clubfirst.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.data.MessageItem
import ru.trifonov.clubfirst.databinding.MessageUser1Binding
import ru.trifonov.clubfirst.databinding.MessageUserBinding

class DialogAdapter(var MessageList : ArrayList<MessageItem>, val context: Context): RecyclerView.Adapter<DialogAdapter.PlaceHolder>() {
    class PlaceHolder(item: View): RecyclerView.ViewHolder(item) {
        val txt = item.findViewById<TextView>(R.id.text_messgae)
        val txt1 = item.findViewById<TextView>(R.id.text_messgae_user)
        val im = item.findViewById<CircleImageView>(R.id.image_user)
        @RequiresApi(Build.VERSION_CODES.Q)
        fun bind(item: MessageItem, context: Context){
            System.out.println(item.mes)
            if (!item.type){
                txt.text = item.mes
            }
            else {
                txt1.text = item.mes
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        if (viewType == 0) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.message_user1, parent, false)
            return PlaceHolder(view)
        }
        else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.message_user, parent, false)
            return PlaceHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (!MessageList[position].type)
        {
            return 0
        }
        else {
            return 1
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.bind(MessageList[position], context)
    }

    override fun getItemCount(): Int {
        return MessageList.size
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
}

