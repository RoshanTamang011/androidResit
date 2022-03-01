package com.dipesh.onlinegadgetsstore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContentProviderCompat.requireContext
import com.dipesh.onlinegadgetsstore.R
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.entity.User
import com.dipesh.onlinegadgetsstore.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class EditUserProfileActivity : AppCompatActivity() {
    private lateinit var etEditFirstName:EditText
    private lateinit var etEditLastName:EditText
    private lateinit var etEditUserName:EditText
    private lateinit var etEditEmail:EditText
    private lateinit var btnEditProfile: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_profile)
        etEditFirstName=findViewById(R.id.etEditFirstName)
        etEditLastName=findViewById(R.id.etEditLastName)
        etEditUserName=findViewById(R.id.etEditUsername)
        etEditEmail=findViewById(R.id.etEditEmail)
        btnEditProfile=findViewById(R.id.btnEditProfile)
        val userInfo= ServiceBuilder.userInfo
        etEditFirstName.setText(userInfo!!.firstName.toString())
        etEditLastName.setText(userInfo!!.lastName.toString())
        etEditUserName.setText(userInfo!!.username.toString())
        etEditEmail.setText(userInfo!!.email.toString())


        btnEditProfile.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    val user = User(firstName = etEditFirstName.text.toString(),
                        lastName = etEditLastName.text.toString(),
                        username = etEditUserName.text.toString(),
                        email = etEditEmail.text.toString()
                        )
                    val repository =UserRepository()
                    val response=repository.updateUser(userInfo?._id!!,user)
                    if(response.success==true){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@EditUserProfileActivity, "User updated successfully", Toast.LENGTH_SHORT).show()
                            ServiceBuilder.userInfo= User(firstName = etEditFirstName.text.toString(),lastName =etEditLastName.text.toString(),username =  etEditUserName.text.toString(), email=etEditEmail.text.toString())
                        }

                    }else{
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@EditUserProfileActivity, "Not Updated", Toast.LENGTH_SHORT).show()
                        }
                    }
                }catch (ex:IOException){
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EditUserProfileActivity, ex.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}