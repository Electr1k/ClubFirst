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
import coil.load
import de.hdodenhof.circleimageview.CircleImageView
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.data.dto.Account

import ru.trifonov.clubfirst.databinding.NameChatUserBinding

class ChatAdapter(
    val items : ArrayList<Account>,
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

        holder.text.text = items[position].name
        holder.lastName.text = items[position].last_message?.text ?: ""
        holder.card.setOnClickListener {
            navController.navigate(R.id.action_chat_to_dialog, Bundle().also { it.putInt("id", items[position].id) })
        }
        if (items[position].participants?.get(1)?.avatar != null){""
            holder.image_user.load(items[position].participants?.get(1)?.avatar)
        }
    }

    class PlaceHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val text: TextView
        val lastName: TextView
        val card : CardView
        val image_user: CircleImageView
        init {
            text = itemView.findViewById(R.id.name_chat_user)
            card = itemView.findViewById(R.id.card_user)
            lastName = itemView.findViewById(R.id.last_message)
            image_user = itemView.findViewById(R.id.image_user)
        }
    }
}
