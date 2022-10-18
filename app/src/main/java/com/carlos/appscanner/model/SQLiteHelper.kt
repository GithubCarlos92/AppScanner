package com.carlos.appscanner.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context):
    SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "data.db"
        private const val TBL_DATA = "Tbl_data"
        //private const val CODIGO = "codigo"
        private const val NOMBRE = "nombre"
        private const val FECHA = "fecha"

    }


    override fun onCreate(p0: SQLiteDatabase?) {
       // val createTable = ("CREATE TABLE " + TBL_DATA + "(" + CODIGO + " INTEGER PRIMARY KEY, " + NOMBRE + "TEXT,"
       //                     + FECHA + " TEXT" + ")")

        val createTable = ("CREATE TABLE " + TBL_DATA + "(" + NOMBRE + " TEXT,"
                + FECHA + " TEXT" + ")")

        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_DATA")
        onCreate(p0)
    }

    fun insertData(data: Data):Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        //contentValues.put(CODIGO, data.codigo)
        contentValues.put(NOMBRE, data.nombre)
        contentValues.put(FECHA, data.fecha)

        val success = db.insert(TBL_DATA,null,contentValues)
        db.close()
        return success
    }


    @SuppressLint("Range")
    fun getAllData():ArrayList<Data> {
        val dataList : ArrayList<Data> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_DATA"
        val db = this.readableDatabase

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery,null)

        }catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return ArrayList()
        }

        var nombre: String
        var fecha: String

        if(cursor.moveToFirst()){
            do {
                    nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                    fecha = cursor.getString(cursor.getColumnIndex("fecha"))

                val data = Data(nombre = nombre, fecha = fecha)
                dataList.add(data)
            }while (cursor.moveToNext())
        }

        return dataList


    }

}