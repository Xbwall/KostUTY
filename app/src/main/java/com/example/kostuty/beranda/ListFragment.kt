package com.example.kostuty.beranda

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kostuty.Adapter.AdapterFavoritesList
import com.example.kostuty.Api.ApiConfig
import com.example.kostuty.Helper.Constant
import com.example.kostuty.Helper.SharePrefHelper
import com.example.kostuty.Kost.InfoKostActivity
import com.example.kostuty.Model.ResponseFavoritesList
import com.example.kostuty.databinding.FragmentListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private var list = ArrayList<ResponseFavoritesList>()
    lateinit var sharePref: SharePrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)

        sharePref = activity?.let { SharePrefHelper(it) }!!
        val id_usr = sharePref.getString(Constant.PREF_ID)

        loadlist(id_usr.toString())

        return binding.root

    }

    private fun loadlist(idUsr: String) {
        val loading = ProgressDialog(context)
        loading.setMessage("Loading, please wait...")
        loading.show()

        ApiConfig.instance.listFavorites(idUsr).enqueue(object :Callback<ArrayList<ResponseFavoritesList>>{
            override fun onResponse(
                call: Call<ArrayList<ResponseFavoritesList>>,
                response: Response<ArrayList<ResponseFavoritesList>>
            ) {
                val rs = response.body()!!
                list.clear()
                list.addAll(rs)
                binding.rvFavorites.setHasFixedSize(true)
                binding.rvFavorites.layoutManager = LinearLayoutManager(context)
                val adp = AdapterFavoritesList(list)
                adp.notifyDataSetChanged()
                binding.rvFavorites.adapter = adp
                loading.hide()
                adp.setOnItemClick(object : AdapterFavoritesList.onAdapterListener{
                    override fun Click(list: ResponseFavoritesList) {
                        sharePref.put(Constant.PREF_ID_KOST,list.id.toString())
                        sharePref.put(Constant.PREF_ID,idUsr)
                        startActivity(Intent(context, InfoKostActivity::class.java))
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<ResponseFavoritesList>>, t: Throwable) {
                loading.hide()
                Log.e("ERROR = ",t.message.toString())
            }

        })
    }

}