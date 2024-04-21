package ru.trifonov.clubfirst.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.adapters.DialogAdapter
import ru.trifonov.clubfirst.common.utils.SettingsData
import ru.trifonov.clubfirst.data.MessageItem
import ru.trifonov.clubfirst.databinding.DialogFragmentBinding
import ru.trifonov.clubfirst.di.ApiModule
import java.util.Calendar
import kotlin.math.max
import kotlin.math.min


class DialogFragment : Fragment(), DialogAdapter.Listener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var selectedDate: TextView
    private lateinit var durationTimeView: TextView
    private lateinit var bind: DialogFragmentBinding
    private lateinit var adapter: DialogAdapter
    private var durationTime = 10
    private var year = 0
    private var hour = 0
    private var minute = 0
    private var day = 0
    private var month = 0

    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0
    private var savedDay = 0
    private var savedMonth = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DialogFragmentBinding.inflate(inflater, container, false)
        initView()

        bind.btnSendMes.setOnClickListener{
            SendMessage(bind.editTextMes.text.toString())
        }

        bind.btnMeeting.setOnClickListener {
            MeetingDialog()
        }
        setupAdapter()
        adapter.createElement(MessageItem("Привет, я такой та такой та", false))
        adapter.createElement(MessageItem("Привет, я такой та такой та", true))

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idChat = arguments?.getInt("id")
        bind.btnBackToChats.setOnClickListener {
            findNavController().popBackStack()
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val messagesResponse = ApiModule.provideApi().getChatMessage(idChat!!, "Bearer ${ SettingsData(requireContext()).getToken()?: ""}")
                messagesResponse.results
            }
            catch (e: Exception){

            }
        }
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun MeetingDialog(){

        val dialog = AlertDialog.Builder(requireContext()).create()

        dialog.window?.setBackgroundDrawable(getDrawable(requireContext(), R.drawable.dialog_rounded_background))
        val alertDialogView = dialog.window!!.decorView
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_meeting, null)
        getDateTimeCalendar()

        selectedDate = dialogView.findViewById(R.id.selected_date)
        selectedDate.text = "${if (day < 10) "0$day" else day}.${if (month < 10) "0$month" else month}.$year ${if (hour < 10) "0$hour" else hour}:${if (minute < 10) "0$minute" else minute}"
        durationTimeView = dialogView.findViewById(R.id.duration_time)
        durationTimeView.text = "Продолжительность: ${durationTime}мин"
        dialogView.findViewById<CardView>(R.id.date_picker_btn).setOnClickListener {

            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        dialogView.findViewById<CardView>(R.id.duration_time_plus).setOnClickListener{
            durationTime = min(60, durationTime + 5)
            durationTimeView.text = "Продолжительность: ${durationTime}мин"
        }

        dialogView.findViewById<CardView>(R.id.duration_time_minus).setOnClickListener{
            durationTime = max(10, durationTime - 5)
            durationTimeView.text = "Продолжительность: ${durationTime}мин"
        }
        dialogView.findViewById<CardView>(R.id.saveBtn).setOnClickListener {
            dialog.cancel()
        }
        val viewGroup = alertDialogView as ViewGroup
        viewGroup.addView(dialogView)
        dialog.show()

    }
    private fun SendMessage(mes : String){
        adapter.createElement(MessageItem(mes, true))
    }

    private fun setupAdapter() {
        adapter= DialogAdapter(this, requireContext())
        bind.rcDialog.layoutManager = LinearLayoutManager(requireContext())
        bind.rcDialog.adapter=adapter
    }

    private fun initView(){
        val str = arguments?.getString("id")
        bind.nameCompanion.text = str
    }

    override fun onClick() {
        TODO("Not yet implemented")
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        selectedDate.text = "${if (savedDay < 10) "0$savedDay" else savedDay}.${if (savedMonth < 10) "0$savedMonth" else savedMonth}.$savedYear ${if (savedHour < 10) "0$savedHour" else savedHour}:${if (savedMinute < 10) "0$savedMinute" else savedMinute}"
    }
}