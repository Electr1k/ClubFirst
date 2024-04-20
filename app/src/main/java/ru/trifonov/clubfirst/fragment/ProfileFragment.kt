package ru.trifonov.clubfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import org.w3c.dom.Text
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.databinding.ProfileFragmentBinding


class ProfileFragment : Fragment() {

    private lateinit var profileFragment: FrameLayout
    private lateinit var cardImage: CardView
    private lateinit var cardInfo: CardView
    private lateinit var mBottomSheet: LinearLayout
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var mBottomSheetInfo: LinearLayout
    private lateinit var mBottomSheetBehaviorInfo: BottomSheetBehavior<LinearLayout>
    private lateinit var moreBtn: Button
    private lateinit var btnListMeeting: ImageButton
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
        btnListMeeting = view.findViewById(R.id.btnListMeeting)
        cardImage = view.findViewById(R.id.card_image)
        cardInfo = view.findViewById(R.id.card_info)
        mBottomSheet = view.findViewById(R.id.bottom_sheet)
        mBottomSheetInfo = view.findViewById(R.id.bottom_sheet_info)
        mBottomSheetBehaviorInfo = BottomSheetBehavior.from(mBottomSheetInfo)
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet)

        mBottomSheetBehavior.isHideable = false
        mBottomSheetBehaviorInfo.skipCollapsed = true
        mBottomSheetBehaviorInfo.isHideable = true
        mBottomSheetBehaviorInfo.state = BottomSheetBehavior.STATE_HIDDEN

        moreBtn.setOnClickListener {
            mBottomSheetBehaviorInfo.state = BottomSheetBehavior.STATE_EXPANDED
        }

        btnListMeeting.setOnClickListener {

        }


    }

    override fun onStart() {
        super.onStart()
        var startTranslation = 0f
        cardImage.post{
            val cardHeight = cardImage.height + cardImage.translationY
            startTranslation = cardImage.translationY
            mBottomSheetBehavior.peekHeight = (profileFragment.height - cardHeight + cardInfo.height * 0.5).toInt()
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