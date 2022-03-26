package com.example.uts2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ReadActivity : AppCompatActivity() {
    lateinit var btnUpdate : Button
    lateinit var btnDelete : Button
    lateinit var txtNamaKota: TextView
    lateinit var txtPositif : TextView
    lateinit var txtSembuh : TextView
    lateinit var txtKematian : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        txtNamaKota = findViewById(R.id.txtNamaKota)
        txtPositif = findViewById(R.id.txtPositif)
        txtSembuh = findViewById(R.id.txtSembuh)
        txtKematian = findViewById(R.id.txtKematian)
        val namaKota = intent.getStringExtra("nama_kota")
        val positif = intent.getStringExtra("jumlah_positif")
        val sembuh = intent.getStringExtra("jumlah_sembuh")
        val kematian = intent.getStringExtra("jumlah_kematian")
        txtNamaKota.setText("Data "+namaKota)
        txtPositif.setText(positif+" Orang Positif")
        txtSembuh.setText(sembuh+" Orang Sembuh")
        txtKematian.setText(kematian+" Orang Meninggal")
        btnUpdate.setOnClickListener {
            val i = Intent(this,UpdateActivity::class.java)
            i.putExtra("nama_kota",namaKota)
            i.putExtra("jumlah_positif",positif)
            i.putExtra("jumlah_sembuh",sembuh)
            i.putExtra("jumlah_kematian",kematian)
            startActivity(i)
        }
        btnDelete.setOnClickListener{
            Toast.makeText(getApplicationContext(),"Data Telah Didelete", Toast.LENGTH_SHORT).show();
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }


    }
}