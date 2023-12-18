package com.example.riusapp

import android.app.AlertDialog
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
            commentTextView = view.findViewById(R.id.commentTextView)
            removebutton = view.findViewById(R.id.removeCommentButton)


            removebutton.setOnClickListener(View.OnClickListener {
                showConfirmationDialog()
                val position = adapterPosition

            })
        }
        private fun showConfirmationDialog() {
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Confirm Deletion")
            builder.setMessage("Are you sure you want to delete this comment?")

            builder.setPositiveButton("Yes") { _, _ ->
                deleteComment()
            }

            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
        }

        private fun deleteComment() {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val removedComment = dataSet[position]
                dataSet = dataSet.toMutableList().apply { removeAt(position) }
                notifyItemRemoved(position)

            }
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
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.comment_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (position >= 0 && position < dataSet.size) {
            val row = dataSet[position]

            viewHolder.commentTextView.text = row[1].toString()


        } else {
            Log.e("UserAdapter", "Invalid position: $position")
        }
    }

    override fun getItemCount() = dataSet.size

    fun updateData(newData: List<List<Any?>>) {
        dataSet = newData
        notifyDataSetChanged()
    }
}