package com.example.praticasqlliteapp

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var etCode: EditText;
    private lateinit var etDescription: EditText;
    private lateinit var etPrice: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCode = findViewById(R.id.etn_code);
        etDescription = findViewById(R.id.et_description);
        etPrice = findViewById(R.id.etnd_price);
    }


    //variable para validar los datos al hacer el crud
    var validation: Boolean = false;
    // writebledatabase permite leer y escribir en la base de datos
    public fun saveItem(view: View){
        val admin = AdminSQLLite(this,"Store",null,1);
        val db: SQLiteDatabase = admin.writableDatabase;

        var code: Int= 0;
        var description: String = "";
        var price: Double = 0.0;
        if(etCode.text.toString().isNotEmpty() && etCode.text.toString().isNotBlank()){
            code = etCode.text.toString().toInt();
            if (etDescription.text.toString().isNotEmpty() && etDescription.text.toString().isNotBlank()) {
                description = etDescription.text.toString();
                if (etPrice.text.toString().isNotEmpty() && etPrice.text.toString().isNotBlank()){
                    price = etPrice.text.toString().toDouble();
                    validation = true;
                }else{
                    Toast.makeText(this, "Campo de precio nulo o vacío. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Campo de descripción nulo o vacío. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Campo de código nulo o vacío. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
        }

        if (validation) {
            var register = ContentValues();
            register.put("item_code", code);
            register.put("item_description", description);
            register.put("item_price", price);

                val cantidad = db.insert("items", null, register);
                if (cantidad==1.toLong()){
                    Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error al guardar artículo", Toast.LENGTH_LONG).show();
                }
            etCode.text.clear();
            etDescription.text.clear();
            etPrice.text.clear();
        }
        db.close();
    }

    //funcion para buscar articulo
    fun findItem(vista:View){
        val admin = AdminSQLLite(this,"Store",null,1);
        val db: SQLiteDatabase = admin.writableDatabase;

        if (etCode.text.toString().isNotEmpty() && etCode.text.toString().isNotBlank()){
            val row = db.rawQuery("select item_description, item_price from items where item_code = ${etCode.text.toString().toInt()}",null);
            if (row.moveToFirst()){
                etDescription.setText(row.getString(0));
                etPrice.setText(row.getString(1));
                println("item:${row.getString(0)} precio: ${row.getString(1)}");
            }else{
                Toast.makeText(this,"No existe el artículo en la base de datos", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Código de busqueda nulo o vacío. Ingresar un código para buscar el artículo.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    fun editItem(vista: View){
        val admin = AdminSQLLite(this,"Store",null,1);
        val db: SQLiteDatabase = admin.writableDatabase;


        var code: Int= 0;
        var description: String = "";
        var price: Double = 0.0;
        var validation: Boolean = false;
        if(etCode.text.toString().isNotEmpty() && etCode.text.toString().isNotBlank()){
            code = etCode.text.toString().toInt();
            if (etDescription.text.toString().isNotEmpty() && etDescription.text.toString().isNotBlank()) {
                description = etDescription.text.toString();
                if (etPrice.text.toString().isNotEmpty() && etPrice.text.toString().isNotBlank()){
                    price = etPrice.text.toString().toDouble();
                    validation = true;
                }else{
                    Toast.makeText(this, "Campo de precio nulo o vacío. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Campo de descripción nulo o vacío. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Campo de código nulo o vacío. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
        }

        if (validation) {
            var register = ContentValues();
            register.put("item_description", description);
            register.put("item_price", price);

            var cantidad = db.update("items",register,"item_code = ${code}",null);
            if (cantidad==1) {
                Toast.makeText(this, "Datos actualizados exitosamente", Toast.LENGTH_SHORT).show();
                db.close();
            }else{
                Toast.makeText(this, "No existe el artículo con ese código", Toast.LENGTH_SHORT).show();
            }
            etCode.text.clear();
            etDescription.text.clear();
            etPrice.text.clear();
        }

    }

    fun deleteItem(vista: View){
        val admin = AdminSQLLite(this,"Store",null,1);
        val db: SQLiteDatabase = admin.writableDatabase;

        var code: Int= 0;
        var validation: Boolean = false;
        if(etCode.text.toString().isNotEmpty() && etCode.text.toString().isNotBlank()){
            code = etCode.text.toString().toInt();
            validation = true;
        }else{
            Toast.makeText(this, "Campo de código nulo o vacío. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
        }

        if (validation) {

            var stateQuery = db.delete("items","item_code = ${code}",null);
            if (stateQuery==1) {
                Toast.makeText(this, "Datos eliminados exitosamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(
                    this,
                    "Error al eliminar artículo",
                    Toast.LENGTH_LONG
                ).show();
            }
            etCode.text.clear();
            etDescription.text.clear();
            etPrice.text.clear();
        }
        db.close();
    }

    fun next(vista:View){
        val window:Intent = Intent(applicationContext,SharePreferencesApp::class.java);
        startActivity(window);
    }

    private fun validateDataFromEditText(editText: EditText,name: String):String{
        var valor: String="";
        if(editText.text.toString().isNotEmpty() && editText.text.toString().isNotBlank()){
            return editText.text.toString();
            validation = true;
        }else{
            Toast.makeText(this, "Campo de ${name} nulo o vacío. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
        };
        return valor;
    }
}