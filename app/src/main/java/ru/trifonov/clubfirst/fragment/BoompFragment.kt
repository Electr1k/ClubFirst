package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.adapters.ChatAdapter
import ru.trifonov.clubfirst.common.utils.SettingsData
import ru.trifonov.clubfirst.data.dto.LoginBody
import ru.trifonov.clubfirst.databinding.ChatFragmentBinding
import ru.trifonov.clubfirst.databinding.DialogBoompBinding
import ru.trifonov.clubfirst.di.ApiModule

class BoompFragment : Fragment() {

    private lateinit var bind: DialogBoompBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DialogBoompBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = view.findViewById<Button>(R.id.button3)
        btn.setOnClickListener { findNavController().navigate(R.id.action_boomp_to_chat) }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userResponse = ApiModule.provideApi().login(LoginBody(arguments?.getString("email")!!))
                val user = userResponse.user
                println(user.avatar)
                requireActivity().runOnUiThread {
                    view.findViewById<ImageView>(R.id.image_profile).load(user.avatar)
                }

            }
            catch (e: Exception){
                println("error")
            }
            try{
                val userResponse2 = ApiModule.provideApi().getCurrentUser(SettingsData(requireContext()).getToken()!!)
                println(userResponse2.avatar)
                requireActivity().runOnUiThread {
                    view.findViewById<ImageView>(R.id.image_profile_1).load(userResponse2.avatar)
                }
            }
            catch (e: Exception){}
        }
    }



}