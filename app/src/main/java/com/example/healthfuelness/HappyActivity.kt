package com.example.healthfuelness

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
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
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_happy.*
import java.util.jar.Manifest


class HappyActivity : AppCompatActivity() {

    private val CAMERA_REQUEST_CODE = 1

    //private lateinit var imageView: ImageView
    //private lateinit var button: Button
    private lateinit var binding: ActivityHappyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHappyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_happy)

        binding.btnCamera.setOnClickListener {
            cameraCheckPermission()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            when(resultCode) {
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    
                    //we are using coroutine image loader (coil)
                    binding.imageView.load(bitmap) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }
                }
            }
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

}