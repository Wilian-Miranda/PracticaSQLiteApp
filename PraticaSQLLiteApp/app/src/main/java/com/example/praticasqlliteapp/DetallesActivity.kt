package com.example.praticasqlliteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetallesActivity : AppCompatActivity() {
    private lateinit var ivImg:ImageView;
    private lateinit var tvName: TextView;
    private lateinit var tvDepartment: TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)
        ivImg = findViewById(R.id.iv_detail_img);
        tvName = findViewById(R.id.tv_detail_name);
        tvDepartment = findViewById(R.id.tv_detail_deparment);

        var bundle:Bundle = getIntent().getExtras()!!;

        ivImg.setImageResource(bundle.getInt("img"));
        tvName.setText(bundle.getString("name"));
        tvDepartment.setText(bundle.getString("department"));

    }
}