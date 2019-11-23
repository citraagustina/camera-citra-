package com.example.cameraapp

import android.Manifest
import android.app.DownloadManager
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Currency
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.net.Authenticator

class MainActivity : AppCompatActivity() {
    val REQUEST_PERMISSION_CODE = 100
    val REQUEST_CAMERA_CODE = 101
    var currentPhoto: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check perizinan
        val daftarIzin = mutableListOf<String>()
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            daftarIzin.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            daftarIzin.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            daftarIzin.add(Manifest.permission.CAMERA)
            if (daftarIzin.size > 0) {
                val iz = arrayOfNulls<String>(daftarIzin.size)
                for (i in 0 until daftarIzin.size) {
                    iz[i] = daftarIzin.get(i)
                    ActivityCompat.requestPermissions(this, iz, REQUEST_PERMISSION_CODE)

                }
            } else {
                // do nothing
            }
        }

    }

    fun takePicture(namaFile: String) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //siapkan file yang akan menyimpang hasil foto
        val filePhoto = File(
            getExternalFilesDir(null), namaFile
        )
        //siapkan public URI, jadi aplikasi kamera bisa menyimpan hasil foto
        //di folder aplikasi kiat
        val uriPhoto = FileProvider.getUriForFile(
            this,
            "com.example.cameraapp.fileprovider",
            filePhoto
        )
        //ambil lokasi file foto tsb, untuk di tampilkan nanti di ImageVIew
        currentPhoto = filePhoto.absolutePath
        //infokan ke aplikasi kamera lokasi tempat menyimpan hasil fotonya
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto)
        //StartActivtyForResult(cameraIntent, REQUEST_CAMERA_CODE)

    }
}
