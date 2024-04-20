package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.trifonov.clubfirst.adapters.ChatAdapter
import ru.trifonov.clubfirst.databinding.ChatFragmentBinding
import ru.trifonov.clubfirst.databinding.DialogBoompBinding

class BoompFragment : Fragment() {

    private lateinit var bind: DialogBoompBinding
    private lateinit var adapter: ChatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DialogBoompBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}