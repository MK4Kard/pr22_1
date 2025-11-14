package com.bignerdranch.android.pr22_2_kma

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class RateFragment : Fragment() {
    lateinit var rate: RateClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rate, container, false)

        var text = view.findViewById<TextView>(R.id.title)

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    fun getResult(rate: String, rate1: String, rate2: String) {
        var key = "69ee2ac8b12138def24d1a2925f69ed8"
        var url="https://currate.ru/api/?get=rates&pairs="+rate+rate1+","+rate+rate2+"&key="+key;
        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                response->
                val obj = JSONObject(response)
                val data = obj.getJSONObject("data")
                Log.d("MyLog", "Response: data=$data")
            },
            {
                Log.d("MyLog","Volley error: $it")
            }
        )
        queue.add(stringRequest)
    }
}