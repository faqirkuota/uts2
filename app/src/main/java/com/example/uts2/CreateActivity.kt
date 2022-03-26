package com.example.uts2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class CreateActivity : AppCompatActivity() {
    lateinit var onCreate: Button
    lateinit var inpIdKec: TextView
    lateinit var inpKec: TextView
    lateinit var inpPositif: TextView
    lateinit var inpSembuh: TextView
    lateinit var inpKematian: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        onCreate = findViewById(R.id.onCreate)
        inpIdKec = findViewById(R.id.inpIdKec)
        inpKec = findViewById(R.id.inpKec)
        inpPositif = findViewById(R.id.inpPositif)
        inpSembuh = findViewById(R.id.inpSembuh)
        inpKematian = findViewById(R.id.inpKematian)
        onCreate.setOnClickListener {
            addRecord()
        }
    }

    private fun addRecord() {
        val idKec = inpIdKec.text.toString()
        val idK = intent.getIntExtra("name_id", 999)
        val kec = inpKec.text.toString()
        val positif = inpPositif.text.toString()
        val sembuh = inpSembuh.text.toString()
        val meninggal = inpKematian.text.toString()
        val databaseHandler: DbHelper = DbHelper(this)
        if (!idKec.isEmpty() || !kec.isEmpty() || !positif.isEmpty() || !sembuh.isEmpty() || !meninggal.isEmpty()) {
            val status =
                databaseHandler.addKecamatan(
                    DataKecamatanModel(
                        idKec.toInt(), idK,
                        kec, positif.toInt(), sembuh.toInt(), meninggal.toInt()
                    )
                )
            val status2 = updateCounter(idK, positif.toInt(), sembuh.toInt(), meninggal.toInt())
            if (status > -1 && status2 > -1) {
                Toast.makeText(getApplicationContext(), "Data Tersimpan", Toast.LENGTH_LONG).show()
                val i = Intent(this, ReadKotaActivity::class.java)
                i.putExtra("id_kota",idK)
                startActivity(i)
            } else {
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

    private fun updateCounter(idK: Int, positif: Int, sembuh: Int, meninggal: Int): Int {
        val totalPositif = intent.getIntExtra("total_positif", 0)
        val totalSembuh = intent.getIntExtra("total_sembuh", 0)
        val totalKematian = intent.getIntExtra("total_kematian", 0)
        val updateTotalPositif = totalPositif + positif
        val updateTotalSembuh = totalPositif + sembuh
        val updateTotalMeninggal = totalPositif + meninggal
        val databaseHandler: DbHelper = DbHelper(this)
        val status = databaseHandler.updateCounter(idK, updateTotalPositif, updateTotalSembuh, updateTotalMeninggal)
        return status
    }
}