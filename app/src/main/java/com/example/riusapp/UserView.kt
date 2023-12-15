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

class UserView : Fragment() {
    private var username: String? = null
    private var reports: Int? = null
    private var ratings: Int? = null
    private var posts: Int? = null

    //TODO replace with data from database
    private val dataset: List<List<Any>> = listOf(
        listOf("My Post", 1, "https://res.cloudinary.com/duxwkkiwn/video/upload/v1699904305/videos/eksfidv6rjq82chy48q5.mp4", "PRISTAN, MARIBOR", "6 OCTOBER"),
        listOf("Your Post", 2, "https://res.cloudinary.com/duxwkkiwn/video/upload/v1699904305/videos/eksfidv6rjq82chy48q5.mp4", "TEKAČEVO, SLATNA", "7 OCTOBER"),
        listOf("Our Post", 3, "https://res.cloudinary.com/duxwkkiwn/video/upload/v1699904305/videos/eksfidv6rjq82chy48q5.mp4", "ŠENTJUR, METROPOLA", "8 OCTOBER"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString("username")
            reports = it.getInt("reports")
            ratings = it.getInt("ratings")
            posts = it.getInt("posts")
        }

        Log.d("UserView", "username: $username, reports: $reports, ratings: $ratings, posts: $posts")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val customAdapter = PostAdapter(dataset)

        val recyclerView: androidx.recyclerview.widget.RecyclerView? = view.findViewById(R.id.recycler_view)
        recyclerView?.adapter = customAdapter

        val btnVerify: Button = view.findViewById(R.id.btnVerify)
        val btnBan: Button = view.findViewById(R.id.btnBan)

        btnVerify.setOnClickListener { showDialog("Verification Dialog\nUsername: $username\nAction: VERIFY") }
        btnBan.setOnClickListener { showDialog("Ban Dialog\nUsername: $username\nAction: BAN") }
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