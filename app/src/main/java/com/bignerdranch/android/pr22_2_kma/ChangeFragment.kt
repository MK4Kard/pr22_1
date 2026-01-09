package com.bignerdranch.android.pr22_2_kma

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.launch
import org.json.JSONObject

class ChangeFragment : Fragment() {
    private var rateId: Int = 0;
    private lateinit var currentRate: RateEntity
    private lateinit var db: RateDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rateId = requireArguments().getInt("ch_rate", -1)

        if (rateId == -1) {
            throw IllegalStateException("supplierId not passed")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change, container, false)

        db = RateDb.getDb(requireContext())

        val spinner = view.findViewById(R.id.spinner) as Spinner
        val spin1 = view.findViewById(R.id.sp1) as Spinner
        val spin2 = view.findViewById(R.id.sp2) as Spinner
        var data = view.findViewById(R.id.ed_data) as EditText
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

        lifecycleScope.launch {
            currentRate = db.rateDao().getById(rateId)
                ?: throw IllegalStateException("Supplier not found: id=$rateId")

            setSpinnerSelection(spinner, currentRate.name_start)
            setSpinnerSelection(spin1, currentRate.name_one)
            setSpinnerSelection(spin2, currentRate.name_two)
            data.setText(currentRate.data_start.toString())
        }

        enter_button.setOnClickListener {
            if(spinner.selectedItem.toString() != spin1.selectedItem.toString() && spinner.selectedItem.toString() != spin2.selectedItem.toString() && spin2.selectedItem.toString() != spin1.selectedItem.toString()
                && data.text.toString().isNotEmpty() && data.text.toString().toInt() > 0){

                getResult(spinner.selectedItem.toString(), spin1.selectedItem.toString(), spin2.selectedItem.toString(), data.text.toString().toInt())
            }
            else {
                val alert = AlertDialog.Builder(requireContext())
                    .setTitle("Ошибка")
                    .setMessage("Проверьте данные")
                    .setPositiveButton("Ok", null)
                    .create()
                    .show()
            }
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    fun getResult(rate: String, rate1: String, rate2: String, dt: Int) {

        val key = "69ee2ac8b12138def24d1a2925f69ed8"
        val url = "https://currate.ru/api/?get=rates&pairs=${rate}${rate1},${rate}${rate2}&key=$key"

        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->

                val obj = JSONObject(response)

                if (!obj.has("data")) {
                    showError("Некорректный ответ сервера")
                    return@StringRequest
                }

                val data = obj.getJSONObject("data")
                val value1 = data.getDouble(rate + rate1)
                val value2 = data.getDouble(rate + rate2)

                currentRate = RateEntity(
                    id = rateId,
                    name_start = rate,
                    data_start = dt.toDouble(),
                    name_one = rate1,
                    name_two = rate2,
                    data_one = value1 * dt,
                    data_two = value2 * dt
                )

                lifecycleScope.launch {
                    db.rateDao().update(currentRate)
                }
            },
            {
                showError("Ошибка сети: $it")
                Log.e("MyLog", "Volley error", it)
            }
        )

        queue.add(stringRequest)
    }

    private fun showError(msg: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Ошибка")
            .setMessage(msg)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun setSpinnerSelection(spinner: Spinner, value: String) {
        val adapter = spinner.adapter as ArrayAdapter<String>
        val position = adapter.getPosition(value)
        if (position != -1) {
            spinner.setSelection(position)
        } else {
            Log.w("ChangeFragment", "Value '$value' not found in spinner")
        }
    }
}