package com.dipesh.onlinegadgetsstore.ui

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dipesh.onlinegadgetsstore.R
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.notification.Notification
import com.dipesh.onlinegadgetsstore.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity(),View.OnClickListener {

    private val permissions= arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    private lateinit var etLoginEmail:EditText
    private lateinit var etLoginPassword:EditText
    private lateinit var tvGoToSignUp:TextView
    private lateinit var loginConstraintLayout:ConstraintLayout

    private lateinit var btnLogin:AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvGoToSignUp=findViewById(R.id.tvGoToSignUp)
        etLoginEmail=findViewById(R.id.etLoginEmail)
        etLoginPassword=findViewById(R.id.etLoginPassword)
        btnLogin=findViewById(R.id.btnLogin)
        loginConstraintLayout=findViewById(R.id.loginConstraint)
        tvGoToSignUp.setOnClickListener(this)
        btnLogin.setOnClickListener(this)

        if(!hasPermission()){
            requestPermission()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvGoToSignUp ->{
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            R.id.btnLogin -> {
                val email = etLoginEmail.text.toString()
                val password = etLoginPassword.text.toString()
                if(formValidator()){
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val repository = UserRepository()
                        val response = repository.checkUser(email, password)
                        if (response.success == true) {
                            saveSharedPref()
                            ServiceBuilder.token = "Bearer " + response.token
                            ServiceBuilder.userInfo= response.data
                            startActivity(
                                    Intent(
                                            this@LoginActivity,
                                            MainActivity::class.java
                                    )
                            )
                            finish()
                        } else {
                            withContext(Dispatchers.Main) {
                                val snack =
                                        Snackbar.make(
                                                loginConstraintLayout,
                                                "Invalid credentials",
                                                Snackbar.LENGTH_LONG
                                        )
                                snack.setAction("OK", View.OnClickListener {
                                    snack.dismiss()
                                })
                                snack.show()
                            }
                        }

                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    this@LoginActivity,
                                    "Invalid email or password", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }


                showLoggedInNotification()
            }
        }
    }

    private fun showLoggedInNotification() {
        val notificationManager = NotificationManagerCompat.from(this)
        val notificationChannels = Notification(this)
        notificationChannels.createNotificationChannels()

        val notification = NotificationCompat.Builder(this, notificationChannels.CHANNEL_1)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Logged In Successfully")
                .setColor(Color.BLUE)
                .build()

        notificationManager.notify(1, notification)

    }

    private fun saveSharedPref(){

        val email = etLoginEmail.text.toString()
        val password = etLoginPassword.text.toString()
        val sharedPref = getSharedPreferences("UserPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("email" , email)
        editor.putString("password" , password)
        editor.apply()
    }
    private fun formValidator():Boolean{
            when{
                etLoginEmail.text.isEmpty()==true->{
                    etLoginEmail.error = "Please enter your email"
                    etLoginEmail.requestFocus()
                    return false
                }
                etLoginPassword.text.isEmpty()==true->{
                    etLoginPassword.error = "Please enter password"
                    etLoginPassword.requestFocus()
                    return false
                }
            }
        return true
    }

    private fun hasPermission():Boolean{
        var hasPermission=true
        for(permission in permissions){
            if(ActivityCompat.checkSelfPermission(
                            this,permission
                ) !=PackageManager.PERMISSION_GRANTED
            ){
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@LoginActivity, permissions, 1)
    }
}