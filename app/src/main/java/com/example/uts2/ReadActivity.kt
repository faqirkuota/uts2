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
    lateinit var txtNamaKecamatan: TextView
    lateinit var txtPositif : TextView
    lateinit var txtSembuh : TextView
    lateinit var txtKematian : TextView
    val databaseHandler: DbHelper = DbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        txtNamaKecamatan = findViewById(R.id.txtNamaKecamatan)
        txtPositif = findViewById(R.id.txtPositif)
        txtSembuh = findViewById(R.id.txtSembuh)
        txtKematian = findViewById(R.id.txtKematian)
        val idKec = intent.getStringExtra("id_kec")
        val idKota = intent.getStringExtra("id_kota")
        val dataKec : DataKecamatanModel = databaseHandler.getDataKec(idKec.toString()).get(0)

        txtNamaKecamatan.setText("Data "+dataKec.kecamatanName)
        txtPositif.setText(dataKec.positif.toString()+" Orang Positif")
        txtSembuh.setText(dataKec.sembuh.toString()+" Orang Sembuh")
        txtKematian.setText(dataKec.meninggal.toString()+" Orang Meninggal")
        btnUpdate.setOnClickListener {
            val i = Intent(this,UpdateActivity::class.java)
            i.putExtra("id_kec",idKec)
            i.putExtra("id_kota",idKota)
            startActivity(i)
        }
        btnDelete.setOnClickListener{
            val status =databaseHandler.deleteKec(Integer.parseInt(idKec))
            val status2 = updateCounter(Integer.parseInt(idKota), dataKec.positif
                , dataKec.sembuh, dataKec.meninggal)
            if (status > -1&& status2 > -1) {
                Toast.makeText(getApplicationContext(),"Data Telah Didelete", Toast.LENGTH_SHORT).show();
                val i = Intent(this,ReadKotaActivity::class.java)
                startActivity(i)
            }else{
                Toast.makeText(getApplicationContext(),"Data Gagal Didelete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private fun updateCounter(idK: Int, positif: Int, sembuh: Int, meninggal: Int): Int {
        val dataKota : DataKotaModel = databaseHandler.getDataKota(idK.toString()).get(0)
        val updateTotalPositif = dataKota.totalPositif - positif
        val updateTotalSembuh = dataKota.totalSembuh - sembuh
        val updateTotalMeninggal = dataKota.totalMeninggal - meninggal
        val databaseHandler: DbHelper = DbHelper(this)
        val status = databaseHandler.updateCounter(idK, updateTotalPositif, updateTotalSembuh, updateTotalMeninggal)
        return status
    }
}