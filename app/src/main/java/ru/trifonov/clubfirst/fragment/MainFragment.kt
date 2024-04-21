package ru.trifonov.clubfirst.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.adapters.TagsAdapter
import ru.trifonov.clubfirst.common.utils.SettingsData
import ru.trifonov.clubfirst.data.dto.AccountObject
import ru.trifonov.clubfirst.data.dto.ReactionBody
import ru.trifonov.clubfirst.data.dto.Tag
import ru.trifonov.clubfirst.di.ApiModule
import ru.trifonov.clubfirst.views.SwipeCardItem
import ru.trifonov.clubfirst.views.SwipeDirection
import ru.trifonov.clubfirst.views.SwipeView


class MainFragment : Fragment() {
    private lateinit var swipeView: SwipeView
    private lateinit var selectEmployee: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = SettingsData(requireContext()).getToken()!!
        swipeView = view.findViewById(R.id.swipe_view)
        swipeView.onCardSwiped = { _, it, swipeDirection ->
            println("$it $swipeDirection")
            val rec = (it as SomeUsefulData).data
            CoroutineScope(Dispatchers.IO).launch {
                val api = ApiModule.provideApi()
                try {
                    val body = ReactionBody(if (swipeDirection == SwipeDirection.LEFT) 1 else 2)
                    api.setReactionOnRecommendation((it as SomeUsefulData).recId, body, token = "Bearer $token")
                    requireActivity().runOnUiThread {
                        if (swipeDirection == SwipeDirection.LEFT) findNavController().navigate(R.id.action_main_to_boomp)
                    }
                }
                catch (e: Exception){
                    println(e.message)
                }

            }

        }
        swipeView.onCardSwipingPercentChanged = { viewCard, progress, swipeDirection ->
            val background = viewCard.background as GradientDrawable
            if (swipeDirection == SwipeDirection.RIGHT){
                background.setStroke(6, interpolateColorToRed(progress))
            }
            else{
                background.setStroke(6, interpolateColorToGreen(progress))
            }

        }
        swipeView.onCardBind = { viewItem: View, employee: SomeUsefulData ->

            val title = viewItem.findViewById<TextView>(R.id.title)
            title.text = employee.data.about
            val rv = viewItem.findViewById<RecyclerView>(R.id.tagsRV)
            rv.adapter = TagsAdapter(employee.data.tags)
            val background = viewItem.background as GradientDrawable
            background.setStroke(6 , Color.argb(255, 255, 221, 0))
        }

    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val listRec = ApiModule.provideApi().getRecommendations("Bearer ${ SettingsData(requireContext()).getToken()?: ""}")
                println("Recommendations: $listRec")
                val listData = mutableListOf<SwipeCardItem>()
                listRec.results.map {
                    listData.add(SwipeCardItem(R.layout.employee_item, SomeUsefulData(it.`object`, it.id))
                    )
                }

                requireActivity().runOnUiThread {
                    swipeView.submitData(listData)
                }
            }
            catch (e: Exception){
                println("error")
            }
        }
    }

    data class SomeUsefulData(
        val data: AccountObject,
        val recId: Int
    )

    private fun interpolateColorToRed(progress: Float): Int {
        val startColor = Color.argb(255, 255, 221, 0)
        val endColor = Color.argb(255, 255, 0, 0)

        val startRed = Color.red(startColor)
        val startGreen = Color.green(startColor)
        val startBlue = Color.blue(startColor)
        val endGreen = Color.green(endColor)

        val newGreen = (startGreen - (startGreen - endGreen) * progress).toInt()

        return Color.argb(255, startRed, newGreen, startBlue)
    }

    private fun interpolateColorToGreen(progress: Float): Int {
        val startColor = Color.argb(255, 255, 221, 0)
        val endColor = Color.argb(255, 0, 255, 0)

        val startRed = Color.red(startColor)
        val startGreen = Color.green(startColor)
        val startBlue = Color.blue(startColor)

        val endRed = Color.red(endColor)
        val endGreen = Color.green(endColor)

        val newRed = (startRed - (startRed - endRed) * progress).toInt()
        val newGreen = (startGreen - (startGreen - endGreen) * progress).toInt()

        return Color.argb(255, newRed, newGreen, startBlue)
    }

}