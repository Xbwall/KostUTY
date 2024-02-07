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
import com.example.kostuty.Adapter.AdapterHome
import com.example.kostuty.Adapter.DaftarKostAdapter
import com.example.kostuty.Api.ApiConfig
import com.example.kostuty.Helper.Constant
import com.example.kostuty.Helper.SharePrefHelper
import com.example.kostuty.Kost.InfoKostActivity
import com.example.kostuty.Kost.InfoKostPenjual
import com.example.kostuty.Model.KostModel
import com.example.kostuty.Model.ResponseHomeList
import com.example.kostuty.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var list = ArrayList<ResponseHomeList>()
    lateinit var sharePref: SharePrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        sharePref = activity?.let { SharePrefHelper(it) }!!

        binding.rcHomelist.setHasFixedSize(true)
        binding.rcHomelist.layoutManager = LinearLayoutManager(context)

        listKost()

        binding.btnSearch.setOnClickListener {
            listKost()
        }

        return binding.root

    }

    fun listKost(){
        val loading = ProgressDialog(context)
        loading.setMessage("Loading, please wait...")
        loading.show()

        ApiConfig.instance.carikost(binding.searchEdt.text.toString()).enqueue(  object : Callback<ArrayList<ResponseHomeList>>{
            override fun onResponse(
                call: Call<ArrayList<ResponseHomeList>>,
                response: Response<ArrayList<ResponseHomeList>>
            ) {
                var listresponse = response.body()!!
                list.clear()
                //list = listresponse
                list.addAll(listresponse)
                //listresponse.let{list.addAll(it)}
//                Log.e("image_home",list[1].gambar_kost.toString())
                val adp = AdapterHome(list)
                adp.notifyDataSetChanged()
                binding.rcHomelist.adapter = adp
                loading.hide()
                adp.setOnItemClick(object : AdapterHome.onAdapterListener{
                    override fun OnClick(list: ResponseHomeList) {
                        sharePref.put(Constant.PREF_ID_KOST,list.id.toString())
                        startActivity(Intent(context, InfoKostActivity::class.java))
                    }

                })

            }

            override fun onFailure(call: Call<ArrayList<ResponseHomeList>>, t: Throwable) {
                loading.hide()
                Log.e("ERROR = ",t.message.toString())
            }

        })
    }
}