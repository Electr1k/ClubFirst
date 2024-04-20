package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.adapters.ChatAdapter
import ru.trifonov.clubfirst.databinding.ChatFragmentBinding
import ru.trifonov.clubfirst.databinding.ProfileFragmentBinding


class ChatFragment : Fragment() {

    private lateinit var bind: ChatFragmentBinding
    private lateinit var adapter: ChatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = ChatFragmentBinding.inflate(inflater, container, false)
        setupAdapter()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupAdapter() {
        val items : ArrayList<String> = arrayListOf("Sergey", "Kolya", "Nikita")
        adapter= ChatAdapter(items, requireContext(), findNavController())
        bind.rcChat.layoutManager = LinearLayoutManager(requireContext())
        bind.rcChat.adapter=adapter
    }

}