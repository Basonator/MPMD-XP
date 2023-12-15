package com.example.riusapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.MediaController

class PostAdapter(private var dataSet: List<List<Any?>>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val postTitleTextView: TextView
        val scoreTextView: TextView
        val videoPlayer: VideoView
        val cityTextView: TextView
        val dateTextView: TextView

        init {
            postTitleTextView = view.findViewById(R.id.postTitleTextView)
            scoreTextView = view.findViewById(R.id.scoreTextView)
            videoPlayer = view.findViewById(R.id.videoPlayer)
            cityTextView = view.findViewById(R.id.cityTextView)
            dateTextView = view.findViewById(R.id.dateTextView)

            view.setOnClickListener(View.OnClickListener {
                Log.d("PostAdapter", "Element $adapterPosition clicked.")
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val post = dataSet[adapterPosition]
                    navigateToPostView(post)
                }
            })
        }

        private fun navigateToPostView(post: List<Any?>) {
            val userViewFragment = UserView() // TODO: Change this to PostView once you create it

            val args = Bundle()
            args.putString("postTitle", post[0].toString())
            args.putInt("ratings", post[1] as Int)
            args.putString("link", post[2].toString())
            args.putString("location", post[3].toString())
            args.putString("date", post[4].toString())
            userViewFragment.arguments = args

            val transaction = (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, userViewFragment) // TODO: Change this to PostView once you create it
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.post_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (position >= 0 && position < dataSet.size) {
            val post = dataSet[position]
            if (post.size >= 5) { // Assuming the video link is at index 2
                viewHolder.postTitleTextView.text = post[0].toString()
                viewHolder.scoreTextView.text = post[1].toString()
                viewHolder.cityTextView.text = post[3].toString()
                viewHolder.dateTextView.text = post[4].toString()

                // Set up VideoView with MediaController
                val mediaController = MediaController(viewHolder.itemView.context)
                viewHolder.videoPlayer.setMediaController(mediaController)

                // Set the video link
                val videoLink = post[2].toString()
                viewHolder.videoPlayer.setVideoURI(Uri.parse(videoLink))

                // Start playing the video
                //viewHolder.videoPlayer.start()
            } else {
                Log.e("PostAdapter", "Invalid row size for position $position")
            }
        } else {
            Log.e("PostAdapter", "Invalid position: $position")
        }
    }

    override fun getItemCount() = dataSet.size
}