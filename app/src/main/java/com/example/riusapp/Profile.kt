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
import com.example.riusapp.backend.RetrofitInstance
import com.example.riusapp.backend.models.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Profile : Fragment() {
    lateinit var inputUsername: EditText
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
        inputUsername = view.findViewById(R.id.loginUsername)
        inputPassword = view.findViewById(R.id.loginPassword)
        loginButton = view.findViewById(R.id.login)

        if (app.isLoggedIn()) {
            var title = view.findViewById<TextView>(R.id.titleText)
            title.visibility = View.GONE
            inputUsername.visibility = View.GONE
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
        transaction.replace(R.id.frame_layout, Profile())
        transaction.addToBackStack(null)
        transaction.commit()
    }
    fun login(){
        var username = inputUsername.text
        var password = inputPassword.text

        if (username.isEmpty()) {
            inputUsername.error = "Username cannot be empty!";
        }

        if (password.isEmpty()) {
            inputPassword.error = "Password cannot be empty!";
        }

        if(!username.isEmpty() && !password.isEmpty()) {
            val loginRequest = LoginRequest(username.toString(), password.toString())
            // Perform login using Retrofit
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val response = RetrofitInstance.api.login(loginRequest)

                    if (response.isSuccessful) {
                        // Login successful
                        Log.d("Login", "Login successful")

                        app.login()

                        // Navigate to the Home fragment
                        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.frame_layout, Home())
                        transaction.addToBackStack(null)
                        transaction.commit()
                    } else {
                        // Login failed
                        Log.e("Login", "Login failed: ${response.message()}")
                    }
                } catch (e: Exception) {
                    // Exception during login
                    Log.e("Login", "Exception during login: ${e.message}")
                }
            }
        }
    }
}