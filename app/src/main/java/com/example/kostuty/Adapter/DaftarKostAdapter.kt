package com.example.kostuty.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kostuty.Api.ApiConfig
import com.example.kostuty.Model.KostModel
import com.example.kostuty.Model.ResponseHomeList
import com.example.kostuty.R
import com.example.kostuty.databinding.ItemDaftarkostBinding

class DaftarKostAdapter(val list: ArrayList<KostModel>):RecyclerView.Adapter<DaftarKostAdapter.ViewHolder>() {

    private var onItemClickCallback: onAdapterListener? = null



    fun setOnItemClick(onItemClickCallback: onAdapterListener){
        this.onItemClickCallback = onItemClickCallback
    }
    inner class ViewHolder(private val itemBind: ItemDaftarkostBinding):RecyclerView.ViewHolder(itemBind.root) {
        fun bind(bind:KostModel){
            Glide.with(itemView.context)
                .load(ApiConfig.base_url+bind.gambar)
                .centerCrop()
                .placeholder(R.drawable.noimage)
                .into(itemBind.itemImage)
            itemBind.itemNamakost.text = bind.namaKost
            itemBind.itemJarakkost.text = bind.jarak
            itemBind.itemHargakost.text = bind.harga.toString()
            itemBind.contentKost.setOnClickListener{onItemClickCallback?.OnClick(bind)}
            itemBind.itemDelete.setOnClickListener {onItemClickCallback?.IconDeleteClick(bind)}

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val x = ItemDaftarkostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(x)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface onAdapterListener {
        fun OnClick(list: KostModel)

        fun IconDeleteClick(list: KostModel)
    }
}