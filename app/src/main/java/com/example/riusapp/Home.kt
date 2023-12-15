package com.example.riusapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class Home : androidx.fragment.app.Fragment() {
    private lateinit var customAdapter: UserAdapter
    private lateinit var btnReports: Button
    private lateinit var btnRatings: Button
    private lateinit var btnNumOfPosts: Button

    //TODO replace with data from database
    private val dataset: List<List<Any>> = listOf(
        listOf("Bor", 1, 2, 6),
        listOf("Jan", 4, 5, 3),
        listOf("Tim", 7, 3, 2),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customAdapter = UserAdapter(dataset)

        val recyclerView: RecyclerView? = view.findViewById(R.id.recycler_view)
        recyclerView?.adapter = customAdapter

        btnReports = view.findViewById(R.id.btnReports)
        btnRatings = view.findViewById(R.id.btnRatings)
        btnNumOfPosts = view.findViewById(R.id.btnNumOfPosts)

        btnReports.setOnClickListener { filterRecyclerView("REPORTS") }
        btnRatings.setOnClickListener { filterRecyclerView("RATING") }
        btnNumOfPosts.setOnClickListener { filterRecyclerView("POSTS") }
    }

    private fun filterRecyclerView(filter: String) {
        when (filter) {
            "REPORTS" -> {
                val sortedByReports = dataset.sortedByDescending { it[1] as Int}
                customAdapter.updateData(sortedByReports)

                btnReports.setTextColor(resources.getColor(R.color.teal_200))
                btnRatings.setTextColor(resources.getColor(R.color.black))
                btnNumOfPosts.setTextColor(resources.getColor(R.color.black))
            }
            "RATING" -> {
                val sortedByRating = dataset.sortedByDescending { it[2] as Int}
                customAdapter.updateData(sortedByRating)

                btnRatings.setTextColor(resources.getColor(R.color.teal_200))
                btnReports.setTextColor(resources.getColor(R.color.black))
                btnNumOfPosts.setTextColor(resources.getColor(R.color.black))

            }
            "POSTS" -> {
                val sortedByPostNum = dataset.sortedByDescending { it[3] as Int}
                customAdapter.updateData(sortedByPostNum)

                btnNumOfPosts.setTextColor(resources.getColor(R.color.teal_200))
                btnReports.setTextColor(resources.getColor(R.color.black))
                btnRatings.setTextColor(resources.getColor(R.color.black))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}