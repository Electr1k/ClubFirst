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
import ru.trifonov.clubfirst.R
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
        swipeView = view.findViewById(R.id.swipe_view)
        swipeView.onCardSwiped = { _, it, swipeDirection ->
            println("$it $swipeDirection")
            if (swipeDirection == SwipeDirection.LEFT){
                findNavController().navigate(R.id.action_main_to_boomp)
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
            title.text = employee.name
            val background = viewItem.background as GradientDrawable
            background.setStroke(6 , Color.argb(255, 255, 221, 0))
        }
        swipeView.submitData(
            listOf(
                SwipeCardItem(R.layout.employee_item, SomeUsefulData("Mircella")),
                SwipeCardItem(R.layout.employee_item, SomeUsefulData("Frodo")),
                SwipeCardItem(R.layout.employee_item, SomeUsefulData("Aragorn")),
            )
        )
    }

    data class SomeUsefulData(
        val name: String
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