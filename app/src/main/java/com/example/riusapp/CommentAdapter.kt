package com.example.riusapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class CommentAdapter(private var dataSet: List<List<Any?>>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val commentTextView: TextView
        val removebutton: Button


        init {
            commentTextView = view.findViewById(R.id.usernameTextView)
            removebutton = view.findViewById(R.id.reportsTextView)


            view.setOnClickListener(View.OnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val user = dataSet[adapterPosition]
                    navigateToUserView(user)
                }
            })
        }

        private fun navigateToUserView(user: List<Any?>) {
            val userViewFragment = UserView()

            val args = Bundle()
            args.putString("username", user[0].toString())
            args.putInt("reports", user[1] as Int)
            args.putInt("ratings", user[2] as Int)
            args.putInt("posts", user[3] as Int)
            userViewFragment.arguments = args

            val transaction = (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, userViewFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.text_row_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (position >= 0 && position < dataSet.size) {
            val row = dataSet[position]
            if (row.size >= 4) {
                viewHolder.usernameTextView.text = row[0].toString()
                viewHolder.reportsTextView.text = row[1].toString()
                viewHolder.ratingsTextView.text = row[2].toString()
                viewHolder.postsTextView.text = row[3].toString()
            } else {
                Log.e("UserAdapter", "Invalid row size for position $position")
            }
        } else {
            Log.e("UserAdapter", "Invalid position: $position")
        }
    }

    override fun getItemCount() = dataSet.size

    fun updateData(newData: List<List<Any>>) {
        dataSet = newData
        notifyDataSetChanged()
    }
}