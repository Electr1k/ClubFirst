package ru.trifonov.clubfirst.fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.adapters.ChatAdapter
import ru.trifonov.clubfirst.adapters.ListMeetingAdapter
import ru.trifonov.clubfirst.adapters.SpinnerAdapter
import ru.trifonov.clubfirst.adapters.StateSpinnerAdapter
import ru.trifonov.clubfirst.data.Meeting
import ru.trifonov.clubfirst.databinding.ChatFragmentBinding
import ru.trifonov.clubfirst.databinding.ListMeetingFragmentBinding
import ru.trifonov.clubfirst.databinding.ProfileFragmentBinding


class ListMeetingFragment : Fragment() {
    private var items : ArrayList<Meeting> = arrayListOf()
    private lateinit var bind: ListMeetingFragmentBinding
    private lateinit var adapter: ListMeetingAdapter
    private lateinit var btn: Button


    private lateinit var mBottomSheet: LinearLayout
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = ListMeetingFragmentBinding.inflate(inflater, container, false)
        items.add(Meeting("Meet1", "Сергей Бекезин", false, "10:00 13.05"))
        items.add(Meeting("Meet2", "Давид Баженов", true, "10:00 14.05"))
        items.add(Meeting("Meet3", "Николай Трифонов", false, "10:00 15.05"))
        items.add(Meeting("Meet4", "Никита Прокопович", false, "10:00 16.05"))
        bind.listNew.setOnClickListener {
            bind.listNew.setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.main_background))
            bind.listLast.setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.prozracni))
            val items_filter : ArrayList<Meeting> = arrayListOf()
            for (i in items){
                if (!i.type){
                    items_filter.add(i)
                }
            }
            adapter.updateList(items_filter)
        }

        bind.listLast.setOnClickListener {
            bind.listNew.setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.prozracni))
            bind.listLast.setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.main_background))
            val items_filter : ArrayList<Meeting> = arrayListOf()
            for (i in items){
                if (i.type){
                    items_filter.add(i)
                }
            }
            adapter.updateList(items_filter)
        }

        bind.btnBackToProfile.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_button_alpha)
            bind.btnBackToProfile.startAnimation(animation)
            CoroutineScope(Dispatchers.Main).launch {
                BackToProfile()
            }
        }
        setupAdapter()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBottomSheet = view.findViewById(R.id.bottom_sheet_info)
        btn = view.findViewById(R.id.save)
        val spinner = view.findViewById<Spinner>(R.id.position)
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet)
        mBottomSheetBehavior.skipCollapsed = true
        mBottomSheetBehavior.isHideable = true
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        val listState = listOf("Состоялось", "Несостоялось")
        spinner.adapter = StateSpinnerAdapter(requireContext(), listState, layoutInflater)
        spinner.setSelection(0)
        btn.setOnClickListener { view.findViewById<TextView>(R.id.comment).setText(""); mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN }
    }

    private fun setupAdapter() {
        val items_filter : ArrayList<Meeting> = arrayListOf()
        for (i in items){
            if (!i.type){
                items_filter.add(i)
            }
        }
        adapter= ListMeetingAdapter(items_filter, requireContext(), findNavController(), { mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED } )
        bind.rcMeeting.layoutManager = LinearLayoutManager(requireContext())
        bind.rcMeeting.adapter=adapter
    }

    private suspend fun BackToProfile(){
        delay(15)
        findNavController().popBackStack()
    }

}