package com.bignerdranch.android.pr22_2_kma

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.room.util.TableInfo.Column
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class RateFragment : Fragment() {
    lateinit var rt1: TextView
    lateinit var rt2: TextView
    lateinit var rt3: TextView
    lateinit var result1: TextView
    lateinit var result2: TextView
    lateinit var result3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rate, container, false)

        rt1 = view.findViewById<TextView>(R.id.rate1)
        rt2= view.findViewById<TextView>(R.id.rate2)
        rt3 = view.findViewById<TextView>(R.id.rate3)
        result1 = view.findViewById<TextView>(R.id.date1)
        result2 = view.findViewById<TextView>(R.id.date2)
        result3 = view.findViewById<TextView>(R.id.date3)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val composeView = view.findViewById<ComposeView>(R.id.composeView)

        composeView.setContent {
            MainScreen()
        }
    }

    override fun onStart() {
        super.onStart()

        val rate = arguments?.getString("rate")
        val r1 = arguments?.getString("r1")
        val r2 = arguments?.getString("r2")
        val data = arguments?.getString("data")

        getResult(rate.toString(), r1.toString(), r2.toString(), data!!.toInt())
    }

    @SuppressLint("SetTextI18n")
    fun getResult(rate: String, rate1: String, rate2: String, dt: Int) {
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

                val value1 = data.getString(rate + rate1)
                val value2 = data.getString(rate + rate2)

                rt1.text = rate
                rt2.text = rate1
                rt3.text = rate2
                result1.text = dt.toString()
                result2.text = (value1.toDouble()*dt).toString()
                result3.text = (value2.toDouble() * dt).toString()
            },
            {
                Log.d("MyLog","Volley error: $it")
            }
        )
        queue.add(stringRequest)
    }
}