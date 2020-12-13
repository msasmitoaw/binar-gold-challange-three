package com.blank.rockpaperscissors

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() , CallbackMsg {

    private lateinit var name: String
    private lateinit var mode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
    }

    private fun loadData() {
        val url = "https://i.ibb.co/HC5ZPgD/splash-screen1.png"
        val ivTitle = findViewById<ImageView>(R.id.ivTitle)
        Glide.with(this).load(url).into(ivTitle)
        name = intent.getStringExtra("Name").toString()
        mode = intent.getStringExtra("Mode").toString()
        findViewById<TextView>(R.id.tvPlayer1).text = name
        findViewById<TextView>(R.id.tvPlayer2).text = mode
        playground()
    }

    private fun playground() {
        mutableListOf(
            R.id.btnPaperPlayerOne ,
            R.id.btnRockPlayerOne ,
            R.id.btnScissorsPlayerOne ,
            R.id.btnRefresh ,
            R.id.btnClose
        ).forEachIndexed { index , i ->
            findViewById<ImageButton>(i).setOnClickListener {
                val btn = it as ImageButton
                when {
                    index < 3 -> {
                        refresh(false , isAll = false)
                        Log.d(
                            "MainActivity" ,
                            "$name Memilih ${btn.tag.toString().capitalize()}"
                        )
                        Toast.makeText(
                            this ,
                            "$name Memilih ${btn.tag.toString().capitalize()}" ,
                            Toast.LENGTH_SHORT
                        ).show()
                        btn.background =
                            ContextCompat.getDrawable(this , R.drawable.bg_item)
                        val player = Player(btn.tag.toString())
                        val controller = Controller(this , this)
                        controller.setPlayer(player)
                        when (mode) {
                            "CPU" -> {
                                controller.setPlayerTwo()
                                selectComputer(controller.getPlayerTwo()?.bet)
                                controller.playRound()
                            }
                            else -> {
                                selectPlayer(controller)
                            }
                        }
                    }
                    index == 3 -> {
                        refresh(true , isAll = false)
                    }
                    index == 4 -> {
                        finish()
                    }
                }
            }
        }
    }

    private fun refresh(enabled: Boolean , isAll: Boolean) {
        mutableListOf(
            R.id.btnPaperPlayerOne ,
            R.id.btnRockPlayerOne ,
            R.id.btnScissorsPlayerOne ,
            R.id.btnPaperPlayerTwo ,
            R.id.btnRockPlayerTwo ,
            R.id.btnScissorsPlayerTwo
        ).forEachIndexed { index , i ->
            if (isAll) {
                findViewById<ImageButton>(i).isEnabled = enabled
            } else {
                findViewById<ImageButton>(i).isEnabled =
                    if (index < 3) enabled else ! enabled
                if (enabled) findViewById<ImageButton>(i).background = null
            }
        }
    }

    private fun selectPlayer(controller: Controller) {
        mutableListOf(
            R.id.btnPaperPlayerTwo ,
            R.id.btnRockPlayerTwo ,
            R.id.btnScissorsPlayerTwo
        ).forEachIndexed { _ , i ->
            findViewById<ImageButton>(i).setOnClickListener {
                refresh(false , isAll = true)
                val btn = it as ImageButton
                Log.d(
                    "MainActivity" ,
                    "$mode Player 2 Memilih ${btn.tag.toString().capitalize()}"
                )
                Toast.makeText(
                    this ,
                    "$mode Memilih ${btn.tag.toString().capitalize()}" ,
                    Toast.LENGTH_SHORT
                ).show()
                btn.background =
                    ContextCompat.getDrawable(this , R.drawable.bg_item)
                val player = Player(btn.tag.toString())
                controller.setPlayerTwo(player)
                controller.playRound()
            }
        }
    }

    private fun selectComputer(s: String?) {
        Log.d("MainActivity" , "$mode Memilih ${s.toString().capitalize()}")
        Toast.makeText(
            this ,
            "$mode Memilih ${s.toString().capitalize()}" ,
            Toast.LENGTH_SHORT
        ).show()
        mutableListOf(
            R.id.btnPaperPlayerTwo ,
            R.id.btnRockPlayerTwo ,
            R.id.btnScissorsPlayerTwo
        ).forEachIndexed { _ , i ->
            val btn = findViewById<ImageButton>(i)
            btn.background = if (btn.tag == s) {
                ContextCompat.getDrawable(this , R.drawable.bg_item)
            } else {
                null
            }
        }
    }

    override fun result(s: String) {
        val view = LayoutInflater.from(this@MainActivity)
            .inflate(R.layout.custom_dialog , null , false)
        val dialogBuilder = AlertDialog.Builder(this@MainActivity)
        dialogBuilder.setView(view)

        val dialog = dialogBuilder.create()
        val btnBackToMenu = view.findViewById<Button>(R.id.btnBackToMenu)
        val btnReset = view.findViewById<Button>(R.id.btnReset)
        val tvDialogResult = view.findViewById<TextView>(R.id.tvDialogResult)

        btnBackToMenu.setOnClickListener {
            finish()
        }

        btnReset.setOnClickListener {
            refresh(true , isAll = false)
            dialog.dismiss()
        }
        val gameEnd = when (s) {
            getString(R.string.player_one_win) -> "$name\nMENANG!"
            getString(R.string.player_two_win) -> "$mode\nMENANG!"
            else -> "SERI!"
        }
        tvDialogResult.text = gameEnd
        Log.d("MainActivity" , "Hasil : $gameEnd")
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}