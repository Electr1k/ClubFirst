package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.adapters.ChatAdapter
import ru.trifonov.clubfirst.common.utils.SettingsData
import ru.trifonov.clubfirst.data.dto.Account
import ru.trifonov.clubfirst.databinding.ChatFragmentBinding
import ru.trifonov.clubfirst.di.ApiModule
import ru.trifonov.clubfirst.views.SwipeCardItem


class ChatFragment : Fragment() {

    private lateinit var bind: ChatFragmentBinding
    private lateinit var adapter: ChatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = ChatFragmentBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        bind.rcChat.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val responseChats = ApiModule.provideApi().getChats("Bearer ${ SettingsData(requireContext()).getToken()?: ""}")
                println("Recommendations: $responseChats")
                val listData = arrayListOf<Account>()
                responseChats.results.map {
                    listData.add(it)
                }
                requireActivity().runOnUiThread {
                    adapter= ChatAdapter(listData, requireContext(), findNavController())
                    bind.rcChat.adapter=adapter
                }
            }
            catch (e: Exception){
                println("error")
            }
        }
    }

}