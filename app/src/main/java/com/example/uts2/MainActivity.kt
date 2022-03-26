package com.example.uts2

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var btnCreate : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list: ListView = findViewById(R.id.listCovid)
        val arrayList: ArrayList<String> = ArrayList()
        btnCreate = findViewById(R.id.btnCreate)
        btnCreate.setOnClickListener{
            var i = Intent(this,CreateKotaActivity::class.java)
            startActivity(i)
        }
        val kotaList : ArrayList<DataKotaModel> = getPulauList()
        for(namaKota in kotaList){
            arrayList.add(namaKota.kotaName)
        }
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        list.setAdapter(arrayAdapter)
        list.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val clickedItem = kotaList[position]
            val i = Intent(this,ReadKotaActivity::class.java)
            val extras = Bundle()
            extras.putString("id_kota",clickedItem.id.toString())
            extras.putString("nama_kota",clickedItem.kotaName)
            extras.putString("total_positif",clickedItem.totalPositif.toString())
            extras.putString("total_sembuh",clickedItem.totalSembuh.toString())
            extras.putString("total_kematian",clickedItem.totalMeninggal.toString())
            i.putExtras(extras)
            startActivity(i)
        })
    }

    private fun getPulauList(): ArrayList<DataKotaModel> {
        val databaseHandler: DbHelper = DbHelper(this)
        val kotaList: ArrayList<DataKotaModel> = databaseHandler.viewDataKota()
        return kotaList
    }
}