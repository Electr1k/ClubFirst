package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.databinding.ProfileFragmentBinding


class ProfileFragment : Fragment() {

    private lateinit var bind: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = ProfileFragmentBinding.inflate(inflater, container, false)
        //bind.
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}