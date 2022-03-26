package com.example.uts2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresPermission.Read

class ReadKotaActivity : AppCompatActivity() {
    lateinit var btnCreateKec: Button
    lateinit var txtNamaKota: TextView
    lateinit var txtTotalPositif : TextView
    lateinit var txtTotalSembuh : TextView
    lateinit var txtTotalKematian : TextView
    val databaseHandler: DbHelper = DbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_kota)
        btnCreateKec = findViewById(R.id.btnCreateKec)
        val idKota = intent.getStringExtra("id_kota")
        val dataKota : DataKotaModel = databaseHandler.getDataKota(idKota.toString()).get(0)
//        val namaKota = intent.getStringExtra("nama_kota")
//        val totalPositif = intent.getStringExtra("total_positif")
//        val totalSembuh = intent.getStringExtra("total_sembuh")
//        val totalKematian = intent.getStringExtra("total_kematian")
        btnCreateKec.setOnClickListener{
            var i = Intent(this,CreateActivity::class.java)
            val extras = Bundle()
            extras.putString("id_kota",idKota)
            extras.putString("total_positif",dataKota.totalPositif.toString())
            extras.putString("total_sembuh",dataKota.totalSembuh.toString())
            extras.putString("total_kematian",dataKota.totalMeninggal.toString())
            i.putExtras(extras)
            startActivity(i)
        }
        txtNamaKota = findViewById(R.id.txtNamaKota)
        txtTotalPositif = findViewById(R.id.txtTotalPositif)
        txtTotalSembuh = findViewById(R.id.txtTotalSembuh)
        txtTotalKematian = findViewById(R.id.txtTotalKematian)

        txtNamaKota.setText("Data "+dataKota.kotaName)
        txtTotalPositif.setText(dataKota.totalPositif.toString()+" Orang Positif")
        txtTotalSembuh.setText(dataKota.totalSembuh.toString()+" Orang Sembuh")
        txtTotalKematian.setText(dataKota.totalMeninggal.toString()+" Orang Meninggal")
        val list: ListView = findViewById(R.id.listKecamatan)
        val arrayList: ArrayList<String> = ArrayList()
        val kecList : ArrayList<DataKecamatanModel> = getKecList(idKota.toString())
        for(namaKota in kecList){
            arrayList.add(namaKota.kecamatanName)
        }
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        list.setAdapter(arrayAdapter)
        list.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val clickedItem = kecList[position]
            val i = Intent(this,ReadActivity::class.java)
            val extras = Bundle()
            extras.putString("id_kota",clickedItem.id.toString())
            extras.putString("nama_kota",clickedItem.kecamatanName)
            extras.putString("total_positif",clickedItem.positif.toString())
            extras.putString("total_sembuh",clickedItem.sembuh.toString())
            extras.putString("total_kematian",clickedItem.meninggal.toString())
            i.putExtras(extras)
            startActivity(i)
        })
    }
    private fun getKecList(idKota : String): ArrayList<DataKecamatanModel> {
        val idK : Int = idKota.toInt()
        val kecList: ArrayList<DataKecamatanModel> = databaseHandler.viewDataKec(idK)
        return kecList
    }
}