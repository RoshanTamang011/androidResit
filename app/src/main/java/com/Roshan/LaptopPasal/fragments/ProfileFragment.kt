package com.dipesh.onlinegadgetsstore.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.dipesh.onlinegadgetsstore.R
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.entity.User
import com.dipesh.onlinegadgetsstore.repository.UserRepository
import com.dipesh.onlinegadgetsstore.ui.EditUserProfileActivity
import com.dipesh.onlinegadgetsstore.ui.GoogleMapActivity
import com.dipesh.onlinegadgetsstore.ui.LoginActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private lateinit var tvProfileUserName: TextView
    private lateinit var imgUserProfileImage: CircleImageView
    private lateinit var imgLogout: CardView
    private lateinit var btnMap: CardView
    private lateinit var btnEdit: CardView
    private lateinit var btnUploadImage: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        tvProfileUserName = view.findViewById(R.id.tvProfileUserName)
        imgUserProfileImage = view.findViewById(R.id.imgUserProfileImage)
        btnMap = view.findViewById(R.id.btnMap)
        btnEdit = view.findViewById(R.id.btnEdit)
        imgLogout = view.findViewById(R.id.imgLogout)
        btnUploadImage = view.findViewById(R.id.btnImageUpload)

        val user = ServiceBuilder.userInfo
        tvProfileUserName.text = user!!.username

        btnMap.setOnClickListener {
            startActivity(Intent(context, GoogleMapActivity::class.java))
        }

        btnEdit.setOnClickListener {
            startActivity(Intent(context, EditUserProfileActivity::class.java))
        }

        btnUploadImage.setOnClickListener {
            try{
                if(imageUrl !=null){
                    uploadImage(user._id)
                }

            }catch (ex:Exception){

            }
        }

        imgLogout.setOnClickListener {
            val builder= AlertDialog.Builder(context)
            builder.setTitle("Logout")
            builder.setMessage("Do you want to logout?")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Logout"){_,_ ->
                logOut()

            }
            builder.setNegativeButton("Cancel"){_,_ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()



        }

        imgUserProfileImage.setOnClickListener {
            loadPopUpMenu()
        }
        return view
    }

    fun logOut() {
        val sharedPref = this.activity?.getSharedPreferences("UserPref", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putString("email", "")
        editor?.putString("password", "")
        editor?.apply()
        Toast.makeText(requireContext(), "Logged Out", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        this.activity?.finish()
    }


    private fun loadPopUpMenu() {
        val popUpMenu = PopupMenu(context, imgUserProfileImage)
        popUpMenu.menuInflater.inflate(R.menu.gallery_camera, popUpMenu.menu)
        popUpMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()

                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popUpMenu.show()

    }

    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = context?.contentResolver
                val cursor =
                        contentResolver?.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                imgUserProfileImage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                imgUserProfileImage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }

    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }


    private fun uploadImage(userId: String?) {
        val file = File(imageUrl!!)

        val mimeType = getMimeType(file);
//            val requestBody: RequestBody =
//                MultipartBody.Builder().setType(MultipartBody.FORM)
//                    .addFormDataPart("file", file.name,sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
//                    .build()

//            val request: Request = Request.Builder().url(serverURL).post(requestBody).build()


        val reqFile =
                RequestBody.create(MediaType.parse(mimeType!!), file)
        val body =
                MultipartBody.Part.createFormData("file", file.name, reqFile)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.uploadImage(userId!!, body)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT)
                                .show()
                    }
                }
            } catch (ex: java.lang.Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Mero Error ", ex.localizedMessage)
                    Toast.makeText(context,
                           "Uploaded successfully",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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