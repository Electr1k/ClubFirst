package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import ru.trifonov.clubfirst.data.dto.LoginBody
import ru.trifonov.clubfirst.databinding.ChatFragmentBinding
import ru.trifonov.clubfirst.databinding.DialogBoompBinding
import ru.trifonov.clubfirst.di.ApiModule

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

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userResponse = ApiModule.provideApi().login(LoginBody(arguments?.getString("email")!!))
                val user = userResponse.user
                requireActivity().runOnUiThread {
                    view.findViewById<ImageView>(R.id.image_profile).load(user.avatar)
                }

            }
            catch (e: Exception){
                println("error")
            }
            try{
                val userResponse2 = ApiModule.provideApi().login(LoginBody(arguments?.getString("email")!!))
                val me = userResponse2.user
                requireActivity().runOnUiThread {
                    view.findViewById<ImageView>(R.id.image_profile_1).load(me.avatar)
                }
            }
            catch (e: Exception){}
        }
    }



}