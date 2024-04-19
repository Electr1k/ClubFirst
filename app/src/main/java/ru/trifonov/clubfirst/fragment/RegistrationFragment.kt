package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import ru.trifonov.clubfirst.R


class RegistrationFragment : Fragment() {

    private lateinit var registrationBtn: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrationBtn = view.findViewById(R.id.registrationBtn)

        registrationBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registration_to_main)
        }

    }
}