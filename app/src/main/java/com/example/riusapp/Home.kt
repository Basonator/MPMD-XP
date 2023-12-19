package com.example.riusapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.riusapp.backend.RetrofitInstance
import com.example.riusapp.backend.models.Users
import kotlinx.coroutines.launch

class Home : androidx.fragment.app.Fragment() {
    private lateinit var customAdapter: UserAdapter
    private lateinit var btnReports: Button
    private lateinit var btnRatings: Button
    private lateinit var btnNumOfPosts: Button
    private var dataset: List<List<Any>> = emptyList()
    lateinit var app: MyApplication

    private suspend fun getUsersFromDatabase() {
        try {
            val response = RetrofitInstance.api.getUsers()
            if (response.isSuccessful) {
                val users = response.body() ?: emptyList()
                dataset = convertUsersToDataset(users)
            } else {
                Log.e("UserView", "Error fetching users: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("UserView", "Exception: ${e.message}")
        }
    }

    private fun convertUsersToDataset(users: List<Users>): List<List<Any>> {
        return users.map { user ->
            listOf(user.username, user.reports, user.rating, user.posts, user._id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (app.isLoggedIn()) {
            lifecycleScope.launch {
                getUsersFromDatabase()

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
        }
        else {
            Log.d("HomeFragment", "User is not logged in. Do something here.")
        }
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
        app = (activity?.application as MyApplication)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}