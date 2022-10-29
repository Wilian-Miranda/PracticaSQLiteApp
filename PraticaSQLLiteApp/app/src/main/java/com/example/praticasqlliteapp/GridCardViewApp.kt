package com.example.praticasqlliteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import java.util.ArrayList

class GridCardViewApp : AppCompatActivity() {

    //variables del otro proyecto de clase
    private lateinit var gvTable: GridView;

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_card_view_app)

        for (imagen in listImg){
            arrayDataitem.add(imagen);
        }
        //cargamos datos del grid view
        gvTable = findViewById(R.id.gv_table);

        adapter = AdapterCustome(arrayDataitem,this);
        gvTable.adapter = adapter;
        gvTable.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val windowItemDetail:Intent = Intent(applicationContext,DetallesActivity::class.java);
                windowItemDetail.putExtra("img",data_imagen_array[position]);
                windowItemDetail.putExtra("name",name_array[position]);
                windowItemDetail.putExtra("department",department_array[position]);
                startActivity(windowItemDetail);
                print(""+data_imagen_array[position]+" "+department_array[position]);
            }

        }

    }
}