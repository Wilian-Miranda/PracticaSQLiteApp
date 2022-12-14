package com.example.praticasqlliteapp

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var etCode: EditText;
    private lateinit var etDescription: EditText;
    private lateinit var etPrice: EditText;
/*
    //variables del otro proyecto de clase
    private lateinit var gvTable:GridView;

    //datos qu vamos a ir usando
    var name_array = arrayListOf<String>("Kemberly", "El Manu", "El Pepe","Christina");
    var department_array = arrayListOf<String>("Chalatenango","San Vicente","San Salvador","Chalatenango");
    var data_imagen_array = arrayOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4
    );

    var listImg = arrayOf(
        item(data_imagen_array.get(0),name_array.get(0),department_array.get(0)),
        item(data_imagen_array.get(1),name_array.get(1),department_array.get(1)),
        item(data_imagen_array.get(2),name_array.get(2),department_array.get(2)),
        item(data_imagen_array.get(3),name_array.get(3),department_array.get(3)),
    );
    var arrayDataitem = ArrayList<item>();

    var adapter: AdapterCustome? = null;
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCode = findViewById(R.id.etn_code);
        etDescription = findViewById(R.id.et_description);
        etPrice = findViewById(R.id.etnd_price);
/*
        //cargamos datos del grid view
        gvTable = findViewById(R.id.gv_table);

        adapter = AdapterCustome(arrayDataitem,this);
        gvTable.onItemClickListener = object :AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }*/
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
                    Toast.makeText(this, "Campo de precio nulo o vac??o. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Campo de descripci??n nulo o vac??o. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Campo de c??digo nulo o vac??o. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, "Error al guardar art??culo", Toast.LENGTH_LONG).show();
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
                Toast.makeText(this,"No existe el art??culo en la base de datos", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"C??digo de busqueda nulo o vac??o. Ingresar un c??digo para buscar el art??culo.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, "Campo de precio nulo o vac??o. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Campo de descripci??n nulo o vac??o. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Campo de c??digo nulo o vac??o. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "No existe el art??culo con ese c??digo", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Campo de c??digo nulo o vac??o. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
        }

        if (validation) {

            var stateQuery = db.delete("items","item_code = ${code}",null);
            if (stateQuery==1) {
                Toast.makeText(this, "Datos eliminados exitosamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(
                    this,
                    "Error al eliminar art??culo",
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
            Toast.makeText(this, "Campo de ${name} nulo o vac??o. Es un campo obligatorio", Toast.LENGTH_SHORT).show();
        };
        return valor;
    }

    //el otro proyecto
    fun windowGridView(vista:View){
        val window:Intent = Intent(applicationContext,GridCardViewApp::class.java);
        startActivity(window);
    }

}