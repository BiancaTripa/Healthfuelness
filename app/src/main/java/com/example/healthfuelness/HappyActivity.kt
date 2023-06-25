package com.example.healthfuelness

import com.example.healthfuelness.User.getUsername
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import coil.load
import coil.transform.CircleCropTransformation
import com.example.healthfuelness.databinding.ActivityHappyBinding
import com.google.firebase.database.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_happy.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class HappyActivity : AppCompatActivity() {

    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    private val CAMERA_REQUEST_CODE = 1
    private lateinit var byteArray : ByteArray


    private lateinit var imageAsBitmap: Bitmap

   // private lateinit var imageView: ImageView
  // val imageView = findViewById<ImageView>(R.id.image_view)

    //private lateinit var button: Button
    private lateinit var binding: ActivityHappyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_happy)

        binding = ActivityHappyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_happy)

        binding.btnCamera.setOnClickListener {
            cameraCheckPermission()
        }

        binding.btnSave.setOnClickListener {
            var descriptionTxt = binding.tvDescription.text.toString()
            if (descriptionTxt.isEmpty()) {
                descriptionTxt = "Sorry, you don't have a description"
            }
            val date = Calendar.getInstance().time
            val currentDate = date.toString("dd/MM/yyyy")

            val user = getUsername()
            val galleryRef: DatabaseReference = databaseReference
                .child("users")
                .child(user)
                .child("gallery")
            val photoId = galleryRef.push().key


            //add to realtime database
            val getContext = this
            databaseReference.child("users").child(user)
                .addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {


                        if (photoId != null) {
                            databaseReference.child("users").child(user).child("gallery").child(
                                photoId
                            ).child("date").setValue(currentDate)
                        }
                        if (photoId != null) {
                            databaseReference.child("users").child(user).child("gallery").child(
                                photoId
                            ).child("description").setValue(descriptionTxt)
                        }
                        //val imagePath = getLatestEmulatorImagePath(applicationContext)
                        if (byteArray != null) {
                            // Upload the image to Firebase or perform any other operations
                            //val imageFile = File(imagePath)
                            //val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)

                            // Convert the image to bytes
                            //val stream = ByteArrayOutputStream()
                            //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            //val imageData = stream.toByteArray()
                            val imageList: List<Int> = byteArray.map { byte -> byte.toInt() }
                            if (photoId != null) {
                                databaseReference.child("users").child(user).child("gallery").child(
                                    photoId
                                ).child("imagePath").setValue(imageList).addOnSuccessListener {
                                    // Image uploaded successfully
                                    // You can perform additional operations here, if needed
                                    println("Image uploaded successfully")
                                }
                                    .addOnFailureListener { exception ->
                                        // Handle any errors that occur during the upload process
                                        println("Failed to upload image. Error: ${exception.message}")
                                    }
                            }


                        } else {
                            // Handle case when no image is found
                            //val imageList: List<Int> = byteArray.map { byte -> byte.toInt() }
                            if (photoId != null) {
                                databaseReference.child("users").child(user).child("gallery").child(
                                    photoId
                                ).child("imagePath").setValue("NO image found")
                            }
                            println("No image found.")
                        }

                            /*
                        databaseReference.child("users").child(user).child("gallery")
                            .child("image").setValue(imageAsBitmap)*/

                            Toast.makeText(
                                getContext,
                                "Happy added successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            //go to home page
                            val intent = Intent(getContext, HomeActivity::class.java)
                            startActivity(intent)

                    }

                        override fun onCancelled(error: DatabaseError) {
                            println("The write failed: " + error.code);
                        }





                })
        }



        /*
        imageView = findViewById(R.id.image_view)
        button = findViewById(R.id.button_take_picture)

        button.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()

            }
            /*
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            } else {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
             */
        }

         */

    }

    private fun cameraCheckPermission() {

        Dexter.withContext(this)
            .withPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                        report?.let {
                            if (report.areAllPermissionsGranted()) {
                                camera()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        showRelationalDialogForPermission()
                    }

                }
        ).onSameThread().check()
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }


    //returns path to the last saved image
    fun getLatestEmulatorImagePath(context: Context): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        cursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                val pathIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val imagePath = cursor.getString(pathIndex)
                return imagePath
            }
        }

        return null
    }

    //transform image to bitmap and upload it to firebase
    fun uploadImageToFirebase(imagePath: String): ByteArray {
        // Load the image from the specified path
        val imageFile = File(imagePath)
        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)

        // Convert the image to bytes
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val imageData = stream.toByteArray()

        return imageData
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)  {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE){
                    val bitmap = data?.extras?.get("data") as Bitmap
            bitmap?.let {
                // Convert the bitmap to a byte array
                byteArray = bitmapToByteArray(it)

            }
            //imageView.setImageBitmap(bitmap)




            }
        else {
            byteArray = byteArrayOf(0x00)

        }




    }


    private fun showRelationalDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage("It looks like you have turned off permissions"
            +"required for this feature. It can be enable under App settings!!")
            .setPositiveButton("GO TO SETTINGS") {_,_ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL") {dialog, _ ->
                dialog.dismiss()
            }.show()

    }







/*
    fun takePhoto(view: View) {
        //start an intent to capture image
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //start the result
        //check if the task can be performed or not
        if (intent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(intent, REQUEST_CODE)
        } else {
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //the user has successfully taken a picture from camera
            val bitmap = data?.extras?.get("data") as Bitmap //where camera will store a low quality of the image
            imageView.setImageBitmap(bitmap)

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

 */

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
}