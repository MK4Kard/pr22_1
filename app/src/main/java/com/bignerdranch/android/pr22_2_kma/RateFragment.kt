package com.bignerdranch.android.pr22_2_kma

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import com.google.android.material.snackbar.Snackbar

class RateFragment : Fragment() {
    lateinit var recycler: RecyclerView
    lateinit var delete: AppCompatButton
    lateinit var change: AppCompatButton
    val adapter = RateAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rate, container, false)

        recycler = view.findViewById<RecyclerView>(R.id.recycler)
        delete = view.findViewById<AppCompatButton>(R.id.delete_btn)
        change = view.findViewById<AppCompatButton>(R.id.change_btn)

        val database = RateDb.getDb(requireContext())
        adapter.data = database.rateDao().getAll()

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        delete.setOnClickListener {
            try {
                val database = RateDb.getDb(requireContext())
                database.rateDao().deleteRate(adapter.selectedRate)
                adapter.data = database.rateDao().getAll()
            }
            catch (e:Exception){
                Snackbar.make(view, "Ошибка удаления", Snackbar.LENGTH_SHORT).show()
            }
        }

        change.setOnClickListener {
            try {
                val database = RateDb.getDb(requireContext())

                val bundle = Bundle()
                bundle.putInt("ch_rate", adapter.selectedRate.id)

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ChangeFragment().apply {
                        arguments = bundle
                    })
                    .addToBackStack(null)
                    .commit()
            }
            catch (e:Exception){
                Snackbar.make(view, "Выберите данное", Snackbar.LENGTH_SHORT).show()
            }
        }

        return view
    }
}