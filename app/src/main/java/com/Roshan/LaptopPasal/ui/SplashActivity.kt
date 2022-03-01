package com.dipesh.onlinegadgetsstore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dipesh.onlinegadgetsstore.R
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            val sharedPref = getSharedPreferences("UserPref", MODE_PRIVATE)
            val email = sharedPref.getString("email", "")
            val password = sharedPref.getString("password", "")

            if(!sharedPref.contains("email")){
                startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
            }else{
                try{
                    val repository = UserRepository()
                    val response = repository.checkUser(email!!, password!!)
                    if (response.success == true) {
                        ServiceBuilder.token = "Bearer " + response.token
                        ServiceBuilder.userInfo=response.data
                        startActivity(
                            Intent(
                                this@SplashActivity,
                                MainActivity::class.java
                            )
                        )

                }else{
                    startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
                }
            }catch (ex:Exception){
                    startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
                }
            }
        }
    }
}