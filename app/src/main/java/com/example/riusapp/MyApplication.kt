package com.example.riusapp

import android.app.Application
import android.content.SharedPreferences
import java.util.UUID

class MyApplication : Application(){
    lateinit var thisInstallationsUUID: UUID
    var loggedIn = false

    override fun onCreate() {
        super.onCreate()

        //SharedPreferences accessor defining our installation ID
        val pref = applicationContext.getSharedPreferences("MyApplicationClass", 0)
        val editor: SharedPreferences.Editor = pref.edit()

        if (!uuidExists()) {
            thisInstallationsUUID = UUID.randomUUID()
            editor.putString("UUID", thisInstallationsUUID.toString())
            editor.apply()
        }
    }

    private fun uuidExists(): Boolean {
        val pref = applicationContext.getSharedPreferences("MyApplicationClass", 0)
        return pref.contains("UUID")
    }

    fun login(){
        loggedIn = true
    }
    fun isLoggedIn(): Boolean{
        return loggedIn
    }
    fun logout(){
        loggedIn = false
    }
}
