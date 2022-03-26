package com.example.uts2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dataCovidFaisal"
        private const val DATABASE_VERSION = 3

        private val TABLE_KOTA = "dataKota"
        private val KEY_ID_KOTA = "_id"
        private val KEY_KOTA_NAME = "kotaName"
        private val KEY_TOTAL_POSITIF = "totalPositif"
        private val KEY_TOTAL_SEMBUH = "totalSembuh"
        private val KEY_TOTAL_MENINGGAL = "totalMeninggal"

        private val TABLE_KECAMATAN = "dataKecamatan"
        private val KEY_ID_KECAMATAN = "_id"
        private val KEY_ID_FK_KOTA = "idKota"
        private val KEY_KECAMATAN_NAME = "kecamatanName"
        private val KEY_POSITIF = "positif"
        private val KEY_SEMBUH = "sembuh"
        private val KEY_MENINGGAL = "meninggal"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_KOTA_TABLE = ("CREATE TABLE " + TABLE_KOTA + "("
                + KEY_ID_KOTA + " INTEGER PRIMARY KEY," + KEY_KOTA_NAME + " TEXT,"
                + KEY_TOTAL_POSITIF + " INTEGER," + KEY_TOTAL_SEMBUH + " INTEGER,"
                + KEY_TOTAL_MENINGGAL + " INTEGER"+")")

        val CREATE_KECAMATAN_TABLE = ("CREATE TABLE " + TABLE_KECAMATAN + "("
                + KEY_ID_KECAMATAN + " INTEGER PRIMARY KEY," + KEY_ID_FK_KOTA +" INTEGER," + KEY_KECAMATAN_NAME + " TEXT,"
                + KEY_POSITIF + " INTEGER," + KEY_SEMBUH + " INTEGER,"
                + KEY_MENINGGAL + " INTEGER"+")")

        db.execSQL(CREATE_KOTA_TABLE)
        db.execSQL(CREATE_KECAMATAN_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, arg1: Int, arg2: Int) {
        db.execSQL("DROP TABLE IF EXISTS dataKota");
        db.execSQL("DROP TABLE IF EXISTS dataKecamatan");
        onCreate(db);
    }

    fun viewDataKota(): ArrayList<DataKotaModel> {
        val empList: ArrayList<DataKotaModel> = ArrayList<DataKotaModel>()
        val selectQuery = "SELECT  * FROM $TABLE_KOTA"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var kotaName: String
        var totalPositif: Int
        var totalSembuh: Int
        var totalMeninggal: Int
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID_KOTA))
                kotaName = cursor.getString(cursor.getColumnIndex(KEY_KOTA_NAME))
                totalPositif = cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_POSITIF))
                totalSembuh = cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_SEMBUH))
                totalMeninggal = cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_MENINGGAL))

                val emp = DataKotaModel(id = id, kotaName = kotaName, totalPositif = totalPositif, totalSembuh = totalSembuh,totalMeninggal = totalMeninggal)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    fun viewDataKec(idKota :Int): ArrayList<DataKecamatanModel> {
        val empList: ArrayList<DataKecamatanModel> = ArrayList<DataKecamatanModel>()
        val selectQuery = "SELECT  * FROM $TABLE_KECAMATAN where $KEY_ID_FK_KOTA = $idKota"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var idKota: Int
        var kecamatanName: String
        var positif: Int
        var sembuh: Int
        var meninggal: Int
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID_KECAMATAN))
                idKota = cursor.getInt(cursor.getColumnIndex(KEY_ID_FK_KOTA))
                kecamatanName = cursor.getString(cursor.getColumnIndex(KEY_KECAMATAN_NAME))
                positif = cursor.getInt(cursor.getColumnIndex(KEY_POSITIF))
                sembuh = cursor.getInt(cursor.getColumnIndex(KEY_SEMBUH))
                meninggal = cursor.getInt(cursor.getColumnIndex(KEY_MENINGGAL))
                val emp = DataKecamatanModel(id = id, idKota=idKota,kecamatanName = kecamatanName, positif = positif, sembuh = sembuh,meninggal = meninggal)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    fun addKota(dataKota: DataKotaModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID_KOTA, dataKota.id)
        contentValues.put(KEY_KOTA_NAME, dataKota.kotaName)
        contentValues.put(KEY_TOTAL_POSITIF, 0)
        contentValues.put(KEY_TOTAL_SEMBUH, 0)
        contentValues.put(KEY_TOTAL_MENINGGAL, 0)
        val success = db.insert(TABLE_KOTA, null, contentValues)
        db.close()
        return success
    }

    fun addKecamatan(dataKecamatan: DataKecamatanModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID_KECAMATAN, dataKecamatan.id)
        contentValues.put(KEY_ID_FK_KOTA, dataKecamatan.idKota)
        contentValues.put(KEY_KECAMATAN_NAME, dataKecamatan.kecamatanName)
        contentValues.put(KEY_POSITIF, dataKecamatan.positif)
        contentValues.put(KEY_SEMBUH, dataKecamatan.sembuh)
        contentValues.put(KEY_MENINGGAL, dataKecamatan.meninggal)
        val success = db.insert(TABLE_KECAMATAN, null, contentValues)
        db.close()
        return success
    }

    fun updateCounter(idK:Int,positif:Int,sembuh:Int,meninggal:Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TOTAL_POSITIF, positif)
        contentValues.put(KEY_TOTAL_SEMBUH, sembuh)
        contentValues.put(KEY_TOTAL_MENINGGAL, meninggal)
        val success = db.update(TABLE_KOTA, contentValues, KEY_ID_KOTA + "=" + idK, null)
        db.close()
        return success
    }

    fun getDataKota(idKota : String): ArrayList<DataKotaModel> {
        val idK : Int = idKota.toInt()
        val empList: ArrayList<DataKotaModel> = ArrayList<DataKotaModel>()
        val selectQuery = "SELECT  * FROM $TABLE_KOTA where $KEY_ID_KOTA = $idK"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var kotaName: String
        var totalPositif: Int
        var totalSembuh: Int
        var totalMeninggal: Int
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID_KOTA))
                kotaName = cursor.getString(cursor.getColumnIndex(KEY_KOTA_NAME))
                totalPositif = cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_POSITIF))
                totalSembuh = cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_SEMBUH))
                totalMeninggal = cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_MENINGGAL))

                val emp = DataKotaModel(id = id, kotaName = kotaName, totalPositif = totalPositif, totalSembuh = totalSembuh,totalMeninggal = totalMeninggal)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
}

