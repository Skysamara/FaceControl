package ru.panorobot.fc

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_photo.*
import java.io.File
import java.io.FileInputStream
import java.lang.Exception

class Photo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        im_photo.setOnClickListener {
            val intent = Intent(this@Photo, MainActivity::class.java)
            startActivity(intent)
        }





        if (ContextCompat.checkSelfPermission(
                this@Photo,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@Photo,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                123
            )
            // TODO: 26.06.2020 Добавить коллбэк
        } else {
            getPhoto()
        }
    }

    private fun getPhoto() {
        val user = intent.getStringExtra("USER")
        val fileName = "/MeridianFC/" + user + ".jpg"
        var f = File(Environment.getExternalStorageDirectory(), fileName)

        try {
            var fso = FileInputStream(f)
            var b: Bitmap = BitmapFactory.decodeStream(fso)
            im_photo.setImageBitmap(b)
            getSupportActionBar()?.setTitle(user)
        } catch (e: Exception) {
            im_photo.setImageBitmap(null)
            getSupportActionBar()?.setTitle("Не найден! " + user)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            getPhoto()
        else Toast.makeText(this, "Необходим доступ для показа фото", Toast.LENGTH_SHORT).show()


//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}