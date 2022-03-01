package com.dipesh.onlinegadgetsstore.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.dipesh.onlinegadgetsstore.R
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.entity.User
import com.dipesh.onlinegadgetsstore.repository.UserRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var imgUserImage:ImageView
    private lateinit var etFirstName:EditText
    private lateinit var etLastName: EditText
    private lateinit var etUserName:EditText
    private lateinit var etEmail:EditText
    private lateinit var etPassword:EditText
    private lateinit var etConfirmPassword:EditText
    private lateinit var btnRegister:AppCompatButton
    private lateinit var tvGoToLogin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        etFirstName=findViewById(R.id.etFirstName)
        etLastName=findViewById(R.id.etLastName)
        etUserName=findViewById(R.id.etUserName)
        etEmail=findViewById(R.id.etEmail)
        etPassword=findViewById(R.id.etPassword)
        etConfirmPassword=findViewById(R.id.etConfirmPassword)
        btnRegister=findViewById(R.id.btnRegister)
        tvGoToLogin=findViewById(R.id.tvGoToLogin)
        tvGoToLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvGoToLogin ->{
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.btnRegister -> {
                val firstName = etFirstName.text.toString()
                val lastName = etLastName.text.toString()
                val userName = etUserName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()
                if (isValid()) {
                    if (password != confirmPassword) {
                        etPassword.error = "Password does not match"
                        etPassword.requestFocus()
                        return
                    } else {
                        val user = User(firstName=firstName, lastName = lastName, username = userName, email = email, password = password)
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val repository = UserRepository()
                                val response = repository.registerUser(user)
                                if (response.success == true) {
                                    ServiceBuilder.token = response.token
                                    withContext(Main) {
                                        Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                                        startActivity(
                                                Intent(this@RegisterActivity,
                                                        LoginActivity::class.java
                                                )
                                        )
                                        finish()
                                    }
                                }
                            } catch (ex: Exception) {
                                withContext(Main) {
                                    Toast.makeText(this@RegisterActivity, ex.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isValid():Boolean{
        when{

            etFirstName.text?.isEmpty()==true->{
                etFirstName.error = "Please enter first name"
                etFirstName.requestFocus()
                return false
            }

            etLastName.text?.isEmpty()==true->{
                etLastName.error = "Please enter last name"
                etLastName.requestFocus()
                return false
            }
            etUserName.text?.isEmpty()==true->{
                etUserName.error = "Please enter user name"
                etUserName.requestFocus()
                return false
            }
            etEmail.text?.isEmpty()==true->{
                etEmail.error = "Please enter your email"
                etEmail.requestFocus()
                return false
            }
            etPassword.text?.isEmpty()==true->{
                etPassword.error = "Please enter the password"
                etPassword.requestFocus()
                return false
            }
            etConfirmPassword.text?.isEmpty()==true->{
                etConfirmPassword.error = "Please re-enter the password"
                etConfirmPassword.requestFocus()
                return false
            }
        }
        return true
    }















    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
}