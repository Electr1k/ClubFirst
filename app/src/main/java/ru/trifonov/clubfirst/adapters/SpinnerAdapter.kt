package ru.trifonov.clubfirst.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import ru.trifonov.clubfirst.data.dto.Position

class SpinnerAdapter(
    context: Context,
    private val adapterList: List<Position>,
    private val layoutInflater: LayoutInflater) : ArrayAdapter<Position>(context, android.R.layout.simple_spinner_item, adapterList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = layoutInflater.inflate(android.R.layout.simple_spinner_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.textView.text = adapterList[position].name

        return view
    }
    private class ViewHolder(view: View) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }
}