package com.codefresher.cau1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codefresher.cau1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var images: ArrayList<Uri?>? = null
    private var position = 0
    private val PICK_IMAGE_CODE = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        images = ArrayList()

        binding.imgSwitcher.setFactory {
            ImageView(applicationContext)
        }

        binding.btnPickImg.setOnClickListener {
            pickImages()
        }

        binding.btnNext.setOnClickListener {
            if (position < images!!.size - 1) {
                position++
                binding.imgSwitcher.setImageURI(images!![position])
            } else {
                Toast.makeText(this, "No more images...", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnPrevious.setOnClickListener {
            if (position > 0) {
                position--
                binding.imgSwitcher.setImageURI(images!![position])
            } else {
                Toast.makeText(this, "No more images...", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun pickImages() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGE_CODE)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                if (data!!.clipData != null) {

                    val count = data.clipData?.itemCount

                    for (i in 0 until  count!!) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        images!!.add(imageUri)
                    }
                    binding.imgSwitcher.setImageURI(images!![0])
                    position = 0
                } else {
                    val imagerUri = data.data
                    binding.imgSwitcher.setImageURI(imagerUri)
                    position = 0

                }
            }
        }
    }
}

