package com.example.riusapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment


class PostOverview : Fragment() {
    private lateinit var postTitleView: TextView
    private lateinit var ratingsTextView: TextView
    private lateinit var btnNumOfPostsTextView: TextView
    private lateinit var linkVideoView: VideoView
    private lateinit var locationTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var btnRemove: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        linkVideoView.setMediaController(mediaController)


        postTitleView.text = postTitle
        ratingsTextView.text = ratings.toString()
        linkVideoView.setVideoURI(Uri.parse(link))
        locationTextView.text = location
        dateTextView.text = date
        btnRemove = view.findViewById(R.id.btnRemove)

        btnRemove.setOnClickListener { showDialog("REMOVE\nAction: REMOVE") }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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