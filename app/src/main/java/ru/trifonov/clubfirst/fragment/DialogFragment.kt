package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.trifonov.clubfirst.adapters.DialogAdapter
import ru.trifonov.clubfirst.data.MessageItem
import ru.trifonov.clubfirst.databinding.DialogFragmentBinding


class DialogFragment : Fragment(), DialogAdapter.Listener {

    private lateinit var bind: DialogFragmentBinding
    private lateinit var adapter: DialogAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DialogFragmentBinding.inflate(inflater, container, false)
        bind.btnSendMes.setOnClickListener{
            SendMessage(bind.editTextMes.text.toString())
        }
        setupAdapter()
        adapter.createElement(MessageItem("Привет, я такой та такой та", false))
        adapter.createElement(MessageItem("Привет, я такой та такой та", true))

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val str = arguments?.getString("id")
        System.out.println(str)

    }

    private fun SendMessage(mes : String){
        adapter.createElement(MessageItem(mes, false))
    }

    private fun setupAdapter() {
        adapter= DialogAdapter(this, requireContext())
        bind.rcDialog.layoutManager = LinearLayoutManager(requireContext())
        bind.rcDialog.adapter=adapter
    }

    override fun onClick() {
        TODO("Not yet implemented")
    }
}