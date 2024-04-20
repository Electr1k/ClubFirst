package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import ru.trifonov.clubfirst.R


class AuthFragment : Fragment() {

    private lateinit var authBtn: CardView
    private lateinit var textTop:TextView
    private lateinit var textBottom:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.auth_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authBtn = view.findViewById(R.id.authBtn)
        textTop = view.findViewById(R.id.top_text)
        textBottom = view.findViewById(R.id.bottom_text)

        authBtn.setOnClickListener {
            findNavController().navigate(R.id.action_auth_to_registration)
        }

    }

    override fun onStart() {
        super.onStart()
        val textAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.auth_text)
        textTop.startAnimation(textAnimation)
        textBottom.startAnimation(textAnimation)
    }
}