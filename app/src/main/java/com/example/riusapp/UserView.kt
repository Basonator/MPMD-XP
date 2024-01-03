package com.example.riusapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.riusapp.backend.RetrofitInstance
import com.example.riusapp.backend.models.Posts
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserView : Fragment() {
    private var username: String? = null
    private var ratings: Int? = null
    private var reports: Int? = null
    private var posts: Int? = null
    private var id: String? = null
    private var dataset: List<List<Any>> = emptyList()


    private suspend fun getPostsByUser(userId: String?) {
        try {
            val response = RetrofitInstance.api.getPostsByUser(userId ?: "")
            if (response.isSuccessful) {
                val posts = response.body() ?: emptyList()
                dataset = convertPostsToDataset(posts)
            } else {
                Log.e("UserView", "Error fetching posts: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("UserView", "Exception: ${e.message}")
        }
    }


    fun formatDate(inputDate: Date): String {
        val desiredFormat = SimpleDateFormat("d/M/Y", Locale.getDefault())
        return desiredFormat.format(inputDate)
    }
    private fun convertPostsToDataset(posts: List<Posts>): List<List<Any>> {
        return posts.map { post ->
            listOf(post.name, post._id, post.link, "", formatDate(post.date), post.rating)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString("username")
            reports = it.getInt("reports")
            ratings = it.getInt("ratings")
            posts = it.getInt("posts")
            id = it.getString("id")
        }

        Log.d("UserView", "username: $username, reports: $reports, ratings: $ratings, posts: $posts, id: $id")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            getPostsByUser(id)

            val customAdapter = PostAdapter(dataset)

            val recyclerView: androidx.recyclerview.widget.RecyclerView? = view.findViewById(R.id.recycler_view)
            recyclerView?.adapter = customAdapter

            val btnVerify: Button = view.findViewById(R.id.btnVerify)
            val btnBan: Button = view.findViewById(R.id.btnBan)

            btnVerify.setOnClickListener {
                showDialog("Verification Dialog\nUsername: $username\nAction: VERIFY")

                lifecycleScope.launch {
                    try {
                        val response = RetrofitInstance.api.verifyUser(id ?: "")
                        if (!response.isSuccessful) {
                            Log.e("UserView", "Error verifying user: ${response.message()}")
                        }
                    } catch (e: Exception) {
                        Log.e("UserView", "Exception: ${e.message}")
                    }
                }
            }

            btnBan.setOnClickListener {
                showDialog("Ban Dialog\nUsername: $username\nAction: BAN")

                lifecycleScope.launch {
                    try {
                        val response = RetrofitInstance.api.banUser(id ?: "")
                        if (!response.isSuccessful) {
                            Log.e("UserView", "Error baning user: ${response.message()}")
                        }
                    } catch (e: Exception) {
                        Log.e("UserView", "Exception: ${e.message}")
                    }
                }
            }
        }
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                //TODO implement verification/ban
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_view, container, false)
        val userTitleTextView: TextView = view.findViewById(R.id.individualUserTitle)
        userTitleTextView.text = username

        return view
    }
}