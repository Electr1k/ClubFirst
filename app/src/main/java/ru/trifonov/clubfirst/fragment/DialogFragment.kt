package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.trifonov.clubfirst.R
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
        initView()

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

        bind.btnBackToChats.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun SendMessage(mes : String){
        adapter.createElement(MessageItem(mes, true))
    }

    private fun setupAdapter() {
        adapter= DialogAdapter(this, requireContext())
        bind.rcDialog.layoutManager = LinearLayoutManager(requireContext())
        bind.rcDialog.adapter=adapter
    }

    private fun initView(){
        val str = arguments?.getString("id")
        bind.nameCompanion.text = str
    }

    override fun onClick() {
        TODO("Not yet implemented")
    }
}