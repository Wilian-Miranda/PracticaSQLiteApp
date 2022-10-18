package com.example.praticasqlliteapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class SharePreferencesApp : AppCompatActivity() {
    lateinit var etName:EditText;
    lateinit var etLastName:EditText;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_preferences_app2)
        etName = findViewById(R.id.et_name);
        etLastName = findViewById(R.id.et_lastname);

        var pref = getSharedPreferences("personData",Context.MODE_PRIVATE);
        var name: String? = pref.getString("name","");
        var lastName: String? = pref.getString("lastName","");
        etName.setText(name);
        etLastName.setText(lastName);
    }

    fun savePreferences(vista:View){
        var pref = getSharedPreferences("personData",Context.MODE_PRIVATE);
        var edit = pref.edit();
        edit.putString("name",etName.text.toString());
        edit.putString("lastName",etLastName.text.toString());

        //enviamos los datos(guardar)
        edit.commit();
        Toast.makeText(this,"Se han guardado las preferencias exitosamente",Toast.LENGTH_SHORT).show();

    }
}