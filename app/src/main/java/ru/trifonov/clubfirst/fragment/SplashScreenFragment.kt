package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import ru.trifonov.clubfirst.R


class SplashScreenFragment : Fragment() {
    private lateinit var appIcon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_screen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appIcon = view.findViewById(R.id.app_icon)
        val textAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.splash_screen_icon)
        appIcon.startAnimation(textAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splash_to_auth)
        }, 1000)
    }
}
