package com.bignerdranch.android.pr22_2_kma

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class EnterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_enter, container, false)

        val spinner = view.findViewById(R.id.spinner) as Spinner
        val spin1 = view.findViewById(R.id.sp1) as Spinner
        val spin2 = view.findViewById(R.id.sp2) as Spinner
        val enter_button = view.findViewById(R.id.next_btn) as Button

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.rates_array,
            android.R.layout.simple_spinner_item
        ).also {  adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spin1.adapter = adapter
            spin2.adapter = adapter
        }

        enter_button.setOnClickListener {
            if(spinner.selectedItem.toString() != spin1.selectedItem.toString() && spinner.selectedItem.toString() != spin2.selectedItem.toString() && spin2.selectedItem.toString() != spin1.selectedItem.toString()){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, RateFragment())
                    .addToBackStack(null)
                    .commit()
            }
            else {
                val alert = AlertDialog.Builder(requireContext())
                    .setTitle("Ошибка")
                    .setMessage("Выберите разные валюты")
                    .setPositiveButton("Ok", null)
                    .create()
                    .show()
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
    }
}