package com.example.uts2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class UpdateActivity : AppCompatActivity() {
    lateinit var onUpdate : Button
    lateinit var txtNamaKota: TextView
    lateinit var txtPositif : TextView
    lateinit var txtSembuh : TextView
    lateinit var txtKematian : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
    }
}