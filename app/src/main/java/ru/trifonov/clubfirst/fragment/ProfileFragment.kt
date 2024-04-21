package ru.trifonov.clubfirst.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.adapters.TagsAdapter
import ru.trifonov.clubfirst.common.utils.SettingsData
import ru.trifonov.clubfirst.di.ApiModule


class ProfileFragment : Fragment() {

    private lateinit var profileFragment: FrameLayout
    private lateinit var cardImage: CardView
    private lateinit var cardInfo: CardView
    private lateinit var mBottomSheet: LinearLayout
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var mBottomSheetInfo: LinearLayout
    private lateinit var mBottomSheetBehaviorInfo: BottomSheetBehavior<LinearLayout>
    private lateinit var moreBtn: Button
    private lateinit var position: TextView
    private lateinit var name: TextView
    private lateinit var nameFull: TextView
    private lateinit var email_full_info: TextView
    private lateinit var birth_day: TextView
    private lateinit var about: TextView
    private lateinit var about_me_full: TextView
    private lateinit var tagsRV: RecyclerView
    private lateinit var image_profile: ImageView
    private lateinit var btnListMeeting: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileFragment = view.findViewById(R.id.profile_fragment)
        moreBtn = view.findViewById(R.id.moreBtn)
        cardImage = view.findViewById(R.id.card_image)
        cardInfo = view.findViewById(R.id.card_info)
        mBottomSheet = view.findViewById(R.id.bottom_sheet)
        tagsRV = view.findViewById(R.id.tagsRV)
        about_me_full = view.findViewById(R.id.about_me)
        mBottomSheetInfo = view.findViewById(R.id.bottom_sheet_info)
        mBottomSheetBehaviorInfo = BottomSheetBehavior.from(mBottomSheetInfo)
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet)
        position = view.findViewById(R.id.position)
        name = view.findViewById(R.id.name)
        nameFull = view.findViewById(R.id.name_full_info)
        birth_day = view.findViewById(R.id.birth_day)
        email_full_info = view.findViewById(R.id.email_full_info)
        about = view.findViewById(R.id.description)
        btnListMeeting = view.findViewById(R.id.btnListMeeting)
        image_profile = view.findViewById(R.id.image_profile)
        mBottomSheetBehavior.isHideable = false
        mBottomSheetBehaviorInfo.skipCollapsed = true
        mBottomSheetBehaviorInfo.isHideable = true
        mBottomSheetBehaviorInfo.state = BottomSheetBehavior.STATE_HIDDEN
        btnListMeeting.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_list_meeting)
        }
        moreBtn.setOnClickListener {
            mBottomSheetBehaviorInfo.state = BottomSheetBehavior.STATE_EXPANDED
        }


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userResponse = ApiModule.provideApi().getCurrentUser("Bearer ${ SettingsData(requireContext()).getToken()?: ""}")
                requireActivity().runOnUiThread {
                    if (userResponse.position!=null){
                        position.text = userResponse.position.name
                    }
                    else{
                        position.text = "Позиция не указана"
                        position.setTextColor(Color.argb(255, 255, 0, 0))
                    }
                    name.text = "${userResponse.first_name} ${userResponse.last_name}"
                    nameFull.text = "${userResponse.first_name} ${userResponse.last_name}"
                    about.text = userResponse.about
                    birth_day.text = userResponse.birth_date ?: ""
                    email_full_info.text = userResponse.email
                    about_me_full.text = userResponse.about
                    tagsRV.adapter = TagsAdapter(userResponse.tags)
                    if (userResponse.avatar != null){
                        image_profile.load(userResponse.avatar)
                    }
                }
            }
            catch (e: Exception){
                println("error")
            }
        }


    }

    override fun onStart() {
        super.onStart()
        var startTranslation = 0f


        cardImage.post{
            val cardHeight = cardImage.height + cardImage.translationY
            startTranslation = cardImage.translationY
            mBottomSheetBehavior.peekHeight = (profileFragment.height - cardHeight + cardInfo.height ).toInt()
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        }
        mBottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                println(slideOffset)
                cardImage.translationY = startTranslation - bottomSheet.height * slideOffset * 0.2f
                val image = cardImage.findViewById<ImageView>(R.id.image_profile)
                image.scaleX = 1.1f + 0.2f * slideOffset
                image.scaleY = 1.1f + 0.2f * slideOffset
            }

        })
    }
}