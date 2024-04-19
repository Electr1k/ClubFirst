package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.adapters.ChatAdapter
import ru.trifonov.clubfirst.databinding.ChatFragmentBinding
import ru.trifonov.clubfirst.databinding.ProfileFragmentBinding


class ChatFragment : Fragment(), ChatAdapter.Listener {

    private lateinit var bind: ChatFragmentBinding
    private lateinit var adapter: ChatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = ChatFragmentBinding.inflate(inflater, container, false)
        bind.btnSendMes.setOnClickListener{
            SendMessage(bind.editTextMes.text.toString())
        }
        setupAdapter()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun SendMessage(mes : String){
        adapter.createElement(mes)
    }

    private fun setupAdapter() {
        adapter= ChatAdapter(this, requireContext())
        bind.rcChat.layoutManager = LinearLayoutManager(requireContext())
        bind.rcChat.adapter=adapter
    }

    override fun onClick() {
        TODO("Not yet implemented")
    }
}