package com.example.praticasqlliteapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AdapterCustome(
    var imgList:ArrayList<item>,
    var context: Context
):BaseAdapter(){
    override fun getCount(): Int {
        return imgList.size;
    }

    override fun getItem(p0: Int): Any {
        return imgList[p0];
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong();
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.row_item,p2,false);
        }
        var imgView: ImageView = view!!.findViewById(R.id.iv_img);
        var tvName: TextView = view!!.findViewById(R.id.tv_detail_name);
        var tvDepartment: TextView = view!!.findViewById(R.id.tv_department);

        val imgIdentificador = imgList[p0];

        imgView.setImageResource(imgIdentificador.img_id);
        tvName.text = imgIdentificador.name;
        tvDepartment.text = imgIdentificador.department;

        return view!!;

    }

}