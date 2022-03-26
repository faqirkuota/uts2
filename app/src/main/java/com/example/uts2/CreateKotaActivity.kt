package com.example.uts2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class CreateKotaActivity : AppCompatActivity() {
    lateinit var onCreate : Button
    lateinit var inpIdKota : TextView
    lateinit var inpNamaKota: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_kota)
        onCreate = findViewById(R.id.onCreate)
        inpIdKota = findViewById(R.id.inpIdKota)
        inpNamaKota = findViewById(R.id.inpNamaKota)
        onCreate.setOnClickListener {
            addRecord()
        }
    }

    private fun addRecord() {
        val idKota = inpIdKota.text.toString()
        val namaKota = inpNamaKota.text.toString()
        val databaseHandler: DbHelper = DbHelper(this)
        if (!idKota.isEmpty() || !idKota.isEmpty()) {
            val status =
                databaseHandler.addKota(DataKotaModel(idKota.toInt(), namaKota,0,0,0))
            if (status > -1) {
                Toast.makeText(getApplicationContext(), "Data Tersimpan", Toast.LENGTH_LONG).show()
                val i = Intent(this,MainActivity::class.java)
                startActivity(i)
            }else{
                Toast.makeText(getApplicationContext(), "ID Telah dipakai", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(
                getApplicationContext(),
                "Data harus di input semua",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}