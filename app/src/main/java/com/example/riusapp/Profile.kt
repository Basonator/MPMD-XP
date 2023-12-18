package com.example.riusapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class Profile : Fragment() {
    lateinit var inputEmail: EditText
    lateinit var inputPassword: EditText
    lateinit var loginButton: Button
    lateinit var app: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        app = (activity?.application as MyApplication)
        inputEmail = view.findViewById(R.id.loginEmail)
        inputPassword = view.findViewById(R.id.loginPassword)
        loginButton = view.findViewById(R.id.login)

        if (app.isLoggedIn()) {
            var title = view.findViewById<TextView>(R.id.titleText)
            title.visibility = View.GONE
            inputEmail.visibility = View.GONE
            inputPassword.visibility = View.GONE
            loginButton.text = "Log Out"
            loginButton.setOnClickListener {
                logout()
            }
        } else {
            // User is not logged in, set up the login button
            loginButton.setOnClickListener {
                login()
            }
        }

        return view
    }

    fun logout(){
        app.logout()
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, Home())
        transaction.addToBackStack(null)
        transaction.commit()
    }
    fun login(){
        var email = inputEmail.text
        var password = inputPassword.text

        if (email.isEmpty()) {
            inputEmail.error = "Email cannot be empty!";
        }

        if (!email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"))) {
            inputEmail.error = "Invalid email address!";
        }

        if (password.isEmpty()) {
            inputPassword.error = "Password cannot be empty!";
        }

        if(!email.isEmpty() && !password.isEmpty() && email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"))) {
            //TODO: login
            Log.d("Login", "Login successful")
            app.login()
            val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, Home())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}