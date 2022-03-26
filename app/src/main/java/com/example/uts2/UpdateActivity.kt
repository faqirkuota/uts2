package com.example.uts2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class UpdateActivity : AppCompatActivity() {
    lateinit var onUpdate : Button
    lateinit var txtNamaKec: TextView
    lateinit var upPositif : TextView
    lateinit var upSembuh : TextView
    lateinit var upKematian : TextView
    val databaseHandler: DbHelper = DbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        onUpdate = findViewById(R.id.btnUpdate)
        txtNamaKec = findViewById(R.id.txtNamaKec)
        upPositif = findViewById(R.id.upPositif)
        upSembuh = findViewById(R.id.upSembuh)
        upKematian = findViewById(R.id.upKematian)

        val idKec = intent.getStringExtra("id_kec")
        val idKota = intent.getStringExtra("id_kota")
        val dataKec : DataKecamatanModel = databaseHandler.getDataKec(idKec.toString()).get(0)

        upPositif.setText(dataKec.positif.toString())
        upSembuh.setText(dataKec.sembuh.toString())
        upKematian.setText(dataKec.meninggal.toString())

        onUpdate.setOnClickListener {
            val positif = upPositif.text.toString()
            val sembuh = upSembuh.text.toString()
            val meninggal = upKematian.text.toString()
            val status = databaseHandler.updateKec(Integer.parseInt(idKec),Integer.parseInt(positif),Integer.parseInt(sembuh),Integer.parseInt(meninggal))
            val status2 = updateCounter(Integer.parseInt(idKota), dataKec.positif
                , dataKec.sembuh, dataKec.meninggal,Integer.parseInt(positif),Integer.parseInt(sembuh),Integer.parseInt(meninggal))
            if (status > -1&& status2 > -1) {
                Toast.makeText(getApplicationContext(),"Data Telah Didelete", Toast.LENGTH_SHORT).show();
                val i = Intent(this,ReadActivity::class.java)
                startActivity(i)
            }else{
                Toast.makeText(getApplicationContext(),"Data Gagal DiUpdate", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private fun updateCounter(idK: Int, positifLama: Int, sembuhLama: Int, meninggalLama: Int
                              , positifBaru: Int, sembuhBaru: Int, meninggalBaru: Int): Int {
        val dataKota : DataKotaModel = databaseHandler.getDataKota(idK.toString()).get(0)
        val updateTotalPositif = dataKota.totalPositif - positifLama + positifBaru
        val updateTotalSembuh = dataKota.totalSembuh - sembuhLama + sembuhBaru
        val updateTotalMeninggal = dataKota.totalMeninggal - meninggalLama + meninggalBaru
        val databaseHandler: DbHelper = DbHelper(this)
        val status = databaseHandler.updateCounter(idK, updateTotalPositif, updateTotalSembuh, updateTotalMeninggal)
        return status
    }
}