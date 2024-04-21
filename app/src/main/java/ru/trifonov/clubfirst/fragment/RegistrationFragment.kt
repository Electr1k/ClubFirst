package ru.trifonov.clubfirst.fragment

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.trifonov.clubfirst.data.dto.Position
import ru.trifonov.clubfirst.di.ApiModule
import ru.trifonov.clubfirst.R
import ru.trifonov.clubfirst.adapters.SpinnerAdapter
import ru.trifonov.clubfirst.data.dto.CreateUserDto
import ru.trifonov.clubfirst.data.dto.Tag
import ru.trifonov.clubfirst.data.dto.TagPagination
import java.io.File
import java.util.Calendar


class RegistrationFragment : Fragment(), DatePickerDialog.OnDateSetListener, AdapterView.OnItemClickListener{

    private lateinit var registrationBtn: Button
    private lateinit var lastName: EditText
    private lateinit var firstName: EditText
    private lateinit var timePreferences: EditText
    private lateinit var about: EditText
    private lateinit var position: Spinner
    private lateinit var selectedDate: TextView
    private lateinit var tags: ListView
    private var positionList: List<Position>? = null

    private var selectedTags: MutableList<Tag> =  mutableListOf()
    private var birthDay: String = ""
    private var imageFile: File? = null
    private var year = 0
    private var day = 0
    private var month = 0

    private var savedYear = 0
    private var savedDay = 0
    private var savedMonth = 0
    private lateinit var tagsResponse: TagPagination
    private val REQUEST_IMAGE_PICK = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrationBtn = view.findViewById(R.id.registrationBtn)
        lastName = view.findViewById(R.id.LastName)
        firstName = view.findViewById(R.id.FirstName) // family
        selectedDate = view.findViewById(R.id.selected_date)
        timePreferences = view.findViewById(R.id.timePreferences)
        position = view.findViewById(R.id.position)
        tags = view.findViewById(R.id.tags)
        about = view.findViewById(R.id.about)
        view.findViewById<CardView>(R.id.birth_date).setOnClickListener {
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        view.findViewById<CardView>(R.id.select_image).setOnClickListener {
            openGallery()
        }
        getDateTimeCalendar()
        selectedDate.text = "$year-${if (month < 10) "0$month" else month}-${if (day < 10) "0$day" else day}"

        tags.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        tags.onItemClickListener = this
        registrationBtn.setOnClickListener {
            println("${firstName.text} ${lastName.text} ${birthDay} ${position.selectedItem} ${timePreferences.text} ${about.text} ${tagsResponse.results} ${imageFile}")
            try {
                if (firstName.text.toString().trim().isEmpty()){
                    Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (lastName.text.toString().trim().isEmpty()){
                    Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (birthDay.trim().isEmpty()){
                    Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (timePreferences.text.toString().trim().isEmpty()){
                    Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (about.text.toString().trim().isEmpty()){
                    Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                tagsResponse.count
                if (imageFile == null){
                    Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                CoroutineScope(Dispatchers.IO).launch {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        val requestBody = imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
                        val listTag = mutableListOf<Int>()
                        tagsResponse.results.map { listTag.add(it.id) }
                        println("Tags ${listTag}")
                        println(birthDay)
//                        val user = ApiModule.provideApi().createUser(
//                                first_name = firstName.text.toString(),
//                                last_name = lastName.text.toString(),
//                                birth_date = birthDay,
//                                time_preference = timePreferences.text.toString(),
//                                tags = listTag,
//                                position = (position.selectedItem as Position).id,
//                                about = about.text.toString(),
//                            avatar = MultipartBody.Part.createFormData("avatar", imageFile!!.name, requestBody)
//                        )
                        requireActivity().runOnUiThread {

                        findNavController().navigate(R.id.action_registration_to_main)
                        }
//                        println("New user ${user}")
                    } else {
                        // Запросите разрешение на чтение файлов
                        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 30)
                    }
                }

            }
            catch (e: Exception){
                println("Error ${e.message}")
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        //            findNavController().navigate(R.id.action_registration_to_main)
        }

    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val positionResponse = ApiModule.provideApi().getPositions()
                requireActivity().runOnUiThread {
                    position.adapter = SpinnerAdapter(requireContext(), positionResponse.results!!, layoutInflater)
                }
            }
            catch (e: Exception){
                println("error")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                tagsResponse = ApiModule.provideApi().getTags()
                val tagsList = mutableListOf<String>()
                tagsResponse.results.map { tagsList.add(it.name)}
                requireActivity().runOnUiThread {
                    tags.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_multiple_choice, tagsList)
                }
            }
            catch (e: Exception){
                println("error")
            }
        }
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        birthDay = "$year-${if (month < 10) "0$month" else month}-${if (day < 10) "0$day" else day}"
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year

        getDateTimeCalendar()
        birthDay = "$savedYear-${if (savedMonth < 10) "0$savedMonth" else savedMonth}-${if (savedDay < 10) "0$savedDay" else savedDay}"
        selectedDate.text = birthDay
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val selectedImagePath = getPathFromUri(requireContext(), selectedImageUri)
            imageFile = File(selectedImagePath)
            // Здесь вы можете использовать файл
        }
    }

    private fun getPathFromUri(context: Context, uri: Uri?): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(uri!!, proj, null, null, null)
            val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        } finally {
            cursor?.close()
        }
    }
}