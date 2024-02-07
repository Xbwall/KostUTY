package com.example.kostuty.Adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kostuty.Api.ApiConfig
import com.example.kostuty.Model.KostModel
import com.example.kostuty.Model.ResponseHomeList
import com.example.kostuty.R
import com.example.kostuty.beranda.HomeFragment
import com.example.kostuty.databinding.ItemDaftarkostBinding
import com.example.kostuty.databinding.ItemHomeBinding
import com.squareup.picasso.Picasso

class AdapterHome(val list:ArrayList<ResponseHomeList>):RecyclerView.Adapter<AdapterHome.ViewHolder>() {
    private var onItemClickCallback: onAdapterListener? = null



    fun setOnItemClick(onItemClickCallback: onAdapterListener){
        this.onItemClickCallback = onItemClickCallback
    }
    inner class ViewHolder(private val itemBind: ItemHomeBinding):RecyclerView.ViewHolder(itemBind.root) {
        fun bind(bind: ResponseHomeList){
            Glide.with(itemView.context)
                .load(ApiConfig.base_url+bind.gambar_kost)
                .centerCrop()
                .placeholder(R.drawable.noimage)
                .into(itemBind.imgKost)
            itemBind.HMJudulKost.text = bind.nama_kost
            itemBind.HMJarakkost.text = bind.jarak
            itemBind.HMHargakost.text = bind.harga.toString()
            itemBind.HMcontent.setOnClickListener({onItemClickCallback?.OnClick(bind)})
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val x = ItemHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(x)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface onAdapterListener {
        fun OnClick(list: ResponseHomeList)
    }
}