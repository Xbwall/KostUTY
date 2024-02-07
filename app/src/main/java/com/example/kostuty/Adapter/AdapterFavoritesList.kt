package com.example.kostuty.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kostuty.Model.ResponseFavoritesList
import com.example.kostuty.databinding.ItemListBinding

class AdapterFavoritesList(val list:ArrayList<ResponseFavoritesList>): RecyclerView.Adapter<AdapterFavoritesList.ViewHolder>() {

    private var onItemClickCallback: AdapterFavoritesList.onAdapterListener? = null

    fun setOnItemClick(onItemClickCallback: AdapterFavoritesList.onAdapterListener){
        this.onItemClickCallback = onItemClickCallback
    }

    interface onAdapterListener {
        fun Click(list: ResponseFavoritesList)
    }

    inner class ViewHolder(private val itemBind: ItemListBinding):RecyclerView.ViewHolder(itemBind.root) {
        fun bind(bind: ResponseFavoritesList){
            itemBind.nmaKostFv.text = bind.namaKost
            itemBind.alamatKostFv.text = bind.alamat
            itemBind.cardlistFv.setOnClickListener { onItemClickCallback?.Click(bind) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val x = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(x)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}