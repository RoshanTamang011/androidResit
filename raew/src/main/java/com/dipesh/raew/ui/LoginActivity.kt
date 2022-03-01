package com.dipesh.raew.ui

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dipesh.raew.R
import com.dipesh.raew.api.ServiceBuilder
import com.dipesh.raew.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : WearableActivity() {
    private lateinit var etWearEmail:EditText
    private lateinit var etWearPassword:EditText
    private lateinit var btnWearLogin:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etWearEmail = findViewById(R.id.etWearEmail)
        etWearPassword = findViewById(R.id.etWearPassword)
        btnWearLogin = findViewById(R.id.btnWearLogin)

        btnWearLogin.setOnClickListener {
            val email = etWearEmail.text.toString()
            val password = etWearPassword.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = UserRepository()
                    val response = repository.checkUser(email, password)
                    if (response.success == true) {
                        ServiceBuilder.token = "Bearer " + response.token
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                DashBoardActivity::class.java
                            )
                        )
                        finish()
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Invalid email or password", Toast.LENGTH_SHORT
                            ).show()
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
    }
}