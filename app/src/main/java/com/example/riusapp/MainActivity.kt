package com.example.riusapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.riusapp.backend.ApiService
import com.example.riusapp.backend.RetrofitInstance
import com.example.riusapp.databinding.ActivityMainBinding
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                val response = try {
//                    RetrofitInstance.api.getUsers()
//                    RetrofitInstance.api.verifyUser("654931388fe50431e68ed06e")
//                    RetrofitInstance.api.banUser("654931388fe50431e68ed06e")
//                    RetrofitInstance.api.getPosts()
//                    RetrofitInstance.api.getPost("654a8acefbcd59e6064fc659")
//                    RetrofitInstance.api.getPostsByUser("654931388fe50431e68ed06e")
//                    RetrofitInstance.api.deletePost("65807bba40c84f0ce42018ac")
                    RetrofitInstance.api.getCommentsByPost("654a8adcfbcd59e6064fc663")
//                    RetrofitInstance.api.deleteComment("658083c040c84f0ce42018b0")
                } catch (e: IOException) {
                    Log.e("MainActivity", "IOException: ${e.message}", e)
                    return@repeatOnLifecycle
                } catch (e: HttpException) {
                    Log.e("MainActivity", "HttpException: ${e.message}", e)
                    return@repeatOnLifecycle
                } catch (e: Throwable) {
                    Log.e("MainActivity", "Throwable: ${e.message}", e)
                    return@repeatOnLifecycle
                }

                //  Check for response
                if (response.isSuccessful && response.body() != null) {
                    // Do something with response.body()
                    val users = response.body()!!
                    Log.d("MainActivity", "Users: $users")
                } else {
                    Log.e("MainActivity", "Response not successful. Code: ${response.code()}, Message: ${response.message()}")
                }
            }
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> replaceFragment(Home())
                R.id.profile -> replaceFragment(Profile())
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}