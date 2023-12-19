package com.example.riusapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.riusapp.backend.RetrofitInstance
import com.example.riusapp.backend.models.Comments
import com.example.riusapp.backend.models.Posts
import kotlinx.coroutines.launch


class PostOverview : Fragment() {
    private lateinit var postTitleView: TextView
    private lateinit var ratingsTextView: TextView
    private lateinit var btnNumOfPostsTextView: TextView
    private lateinit var linkVideoView: VideoView
    private lateinit var locationTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var btnRemove: Button
    private lateinit var dataset: List<List<Any?>>
    private var id: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private suspend fun getCommentsbyPost(postId: String?) {
        try {
            val response = RetrofitInstance.api.getCommentsByPost(postId ?: "")
            if (response.isSuccessful) {
                val posts = response.body() ?: emptyList()
                dataset = convertCommentsToDataset(posts)
            } else {
                Log.e("UserView", "Error fetching posts: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("UserView", "Exception: ${e.message}")
        }
    }

    private fun convertCommentsToDataset(posts: List<Comments>): List<List<Any>> {
        return posts.map { post ->
            listOf(post._id, post.contents)
        }
    }

//    private val dataset: List<List<Any?>> = listOf(
//        listOf(1, "Cool workout"),
//        listOf(2, "How did you get such good form?"),
//        listOf(3, "You don't get to skip leg day!")
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_post_overview, container, false)

        val postTitle = arguments?.getString("postTitle")
        val ratings = arguments?.getInt("ratings", 0) // Default value is 0
        val link = arguments?.getString("link")
        val location = arguments?.getString("location")
        val date = arguments?.getString("date")
        id = arguments?.getString("id")

        /*
        *   viewHolder.postTitleTextView.text = post[0].toString()
                viewHolder.scoreTextView.text = post[1].toString()
                viewHolder.cityTextView.text = post[3].toString()
                viewHolder.dateTextView.text = post[4].toString()

                // Set up VideoView with MediaController
                val mediaController = MediaController(viewHolder.itemView.context)
                viewHolder.videoPlayer.setMediaController(mediaController)

                // Set the video link
                val videoLink = post[2].toString()
                viewHolder.videoPlayer.setVideoURI(Uri.parse(videoLink))*/
        postTitleView = view.findViewById(R.id.postTitleTextView)
        ratingsTextView = view.findViewById(R.id.ratingTextView)
        btnNumOfPostsTextView = view.findViewById(R.id.ratingTextView)
        linkVideoView = view.findViewById(R.id.videoPlayer)
        locationTextView = view.findViewById(R.id.cityTextView)
        dateTextView = view.findViewById(R.id.dateTextView)
        val mediaController = MediaController(view.context)




        postTitleView.text = postTitle
        ratingsTextView.text = ratings.toString()
        linkVideoView.setVideoURI(Uri.parse(link))
        linkVideoView.start()
        linkVideoView.setOnCompletionListener {
            linkVideoView.start()
        }
        //linkVideoView
        locationTextView.text = location
        dateTextView.text = date
        btnRemove = view.findViewById(R.id.btnRemove)

        btnRemove.setOnClickListener {
            showDialog("REMOVE\nAction: REMOVE")

            lifecycleScope.launch {
                try {
                    val response = RetrofitInstance.api.deletePost(id ?: "")
                    if (!response.isSuccessful) {
                        Log.e("PostOverview", "Error removing post: ${response.message()}")
                    }
                } catch (e: Exception) {
                    Log.e("PostOverview", "Exception: ${e.message}")
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            getCommentsbyPost(id)

            val customAdapter = CommentAdapter(dataset, lifecycleScope)

            val recyclerView: androidx.recyclerview.widget.RecyclerView? = view.findViewById(R.id.commentRecyclerView)
            recyclerView?.adapter = customAdapter
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
}