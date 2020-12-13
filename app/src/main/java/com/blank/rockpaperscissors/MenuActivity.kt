package com.blank.rockpaperscissors

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MenuActivity : AppCompatActivity() {

    private lateinit var name: String

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        name = intent.getStringExtra("Name").toString()

        val snackBar = Snackbar.make(
            findViewById(R.id.clMenu) ,
            "Selamat Datang $name" ,
            Snackbar.LENGTH_INDEFINITE ,
        )

        snackBar.setAction("Tutup") {
            snackBar.dismiss()
        }

        snackBar.setActionTextColor(getColor(R.color.green))
        snackBar.setBackgroundTint(getColor(R.color.grey_dark))
        snackBar.show()

        findViewById<TextView>(R.id.tvVsPlayer).text = "$name VS Pemain"
        findViewById<TextView>(R.id.tvVsComputer).text = "$name VS CPU"

        findViewById<LinearLayout>(R.id.llVsPlayer).setOnClickListener {
            accessMenu("Player 2")
        }
        findViewById<LinearLayout>(R.id.llVsComputer).setOnClickListener {
            accessMenu("CPU")
        }

    }

    private fun accessMenu(mode: String) {
        Intent(this , MainActivity::class.java).apply {
            putExtra("Name" , name)
            putExtra("Mode" , mode)
            startActivity(this)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}