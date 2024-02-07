package com.example.kostuty.Kost

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kostuty.Adapter.DaftarKostAdapter
import com.example.kostuty.Api.ApiConfig
import com.example.kostuty.Helper.Constant
import com.example.kostuty.Helper.SharePrefHelper
import com.example.kostuty.Model.KostModel
import com.example.kostuty.Model.ResponseCUD
import com.example.kostuty.beranda.HomeActivity
import com.example.kostuty.beranda.ProfileFragment
import com.example.kostuty.databinding.ActivityDaftarKostBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarKostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDaftarKostBinding
    private val list = ArrayList<KostModel>()
    lateinit var sharePref: SharePrefHelper

    private var id_user:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarKostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharePref = SharePrefHelper(this@DaftarKostActivity)
        id_user = sharePref.getString(Constant.PREF_ID)

        binding.btnKembali.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

        loadlistkost(id_user.toString())

    }

    private fun loadlistkost(id:String) {
        binding.rcDaftarkost.setHasFixedSize(true)
        binding.rcDaftarkost.layoutManager = LinearLayoutManager(this)

        ApiConfig.instance.kostuser(id.toInt()).enqueue(object : Callback<ArrayList<KostModel>>{
            override fun onResponse(
                call: Call<ArrayList<KostModel>>,
                response: Response<ArrayList<KostModel>>
            ) {
                val listresponse = response.body()!!
                list.clear()
                listresponse.let{list.addAll(it)}
                val adp = DaftarKostAdapter(list)
                binding.rcDaftarkost.adapter = adp
                adp.setOnItemClick(object : DaftarKostAdapter.onAdapterListener{
                    override fun OnClick(list: KostModel) {
                        sharePref.put(Constant.PREF_ID_KOST,list.id.toString())
                        startActivity(Intent(this@DaftarKostActivity,InfoKostPenjual::class.java))
                    }

                    override fun IconDeleteClick(list: KostModel) {
                        hitdelete(list.id!!)
                    }

                })
            }

            override fun onFailure(call: Call<ArrayList<KostModel>>, t: Throwable) {
                Log.e("ERROR = ",t.message.toString())
            }

        })
    }

    private fun hitdelete(idkost:Int) {
        ApiConfig.instance.deletekost(idkost.toString().toInt()).enqueue(object : Callback<ResponseCUD>{
            override fun onResponse(call: Call<ResponseCUD>, response: Response<ResponseCUD>) {
                var respon = response.body()!!
                loadlistkost(id_user.toString())
                Toast.makeText(this@DaftarKostActivity,respon.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseCUD>, t: Throwable) {
                Toast.makeText(this@DaftarKostActivity,t.message.toString(), Toast.LENGTH_SHORT).show()
                Log.e("ERROR = ",t.message.toString())
            }
        })
    }
}