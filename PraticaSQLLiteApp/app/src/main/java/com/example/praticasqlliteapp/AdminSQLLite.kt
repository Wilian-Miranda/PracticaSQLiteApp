package com.example.praticasqlliteapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//poder crear la base de datos que despues será llenada desde el mainActivity
//: indicamos que va a heredar la clase SQLOpenHelper que se usara para crear la base de datos
class AdminSQLLite(
    context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int
): SQLiteOpenHelper(context,name,factory,version){
    override fun onCreate(DB: SQLiteDatabase?) {
        //nuestra base de datos va aqui

        //usamos una expresion SQl para que realice un sentencia
        DB?.execSQL("CREATE TABLE items(" +
                "item_code INT PRIMARY KEY NOT NULL," +
                "item_description TEXT," +
                "item_price REAL" +
                ");");
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}