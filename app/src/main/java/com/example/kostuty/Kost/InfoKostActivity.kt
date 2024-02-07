package com.example.kostuty.Kost

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kostuty.Api.ApiConfig
import com.example.kostuty.Helper.Constant
import com.example.kostuty.Helper.SharePrefHelper
import com.example.kostuty.Model.ResponseDetailKost
import com.example.kostuty.Model.UserModel
import com.example.kostuty.R
import com.example.kostuty.beranda.HomeActivity
import com.example.kostuty.databinding.ActivityInfoKostBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoKostActivity : AppCompatActivity() {

    lateinit var sharePref: SharePrefHelper
    private lateinit var binding: ActivityInfoKostBinding
    private var id_kost:Int = 0
    private var id_usr:Int = 0
    private var notelp:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoKostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharePref = SharePrefHelper(this)
        id_kost = sharePref.getString(Constant.PREF_ID_KOST)!!.toInt()
        id_usr = sharePref.getString(Constant.PREF_ID)!!.toInt()


        val loading = ProgressDialog(this)
        loading.setMessage("Loading, please wait...")
        loading.show()



        ApiConfig.instance.detailkost(id_kost!!.toInt()).enqueue(object : Callback<ResponseDetailKost>{
            override fun onResponse(call: Call<ResponseDetailKost>, response: Response<ResponseDetailKost>) {
                val res = response.body()!!
                Glide.with(this@InfoKostActivity)
                    .load(ApiConfig.base_url+res.gambarKost)
                    .centerCrop()
                    .placeholder(R.drawable.noimage)
                    .into(binding.imgGambarkos)
                Log.e("image",ApiConfig.base_url+res.gambarKost)
                binding.txtNamakos.text = res.namaKost
                binding.txtHargakos.text = "Rp "+res.harga.toString()+" / bulan"
                binding.txtJarakkos.text = "Jarak : "+res.jarak + " meter"
                binding.alamat.text = "Lokasi : "+res.alamat
                binding.textGender.text = res.kategori
                binding.txtDeskripsi.text = res.deskripsi
                notelp = res.telepon
            }

            override fun onFailure(call: Call<ResponseDetailKost>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

        })


        cekfavorite()
        loading.hide()


        binding.btnOrder.setOnClickListener({
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:"+notelp))
            startActivity(intent)
        })

        binding.btnWa.setOnClickListener {
            val url = "https://wa.me/+62"+notelp
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        binding.imgFavorites.setOnClickListener {
            favorite()
        }
    }

    private fun favorite() {
        ApiConfig.instance.favorite(id_kost,id_usr).enqueue(object :Callback<UserModel>{
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                startActivity(Intent(this@InfoKostActivity,HomeActivity::class.java))
                Toast.makeText(this@InfoKostActivity,response.body()!!.message,Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Toast.makeText(this@InfoKostActivity,t.message,Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun cekfavorite() {
        Log.e("infokost", id_kost.toString()+", "+id_usr.toString())
        ApiConfig.instance.cekfavorite(id_kost,id_usr).enqueue(object :Callback<UserModel>{
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                val rs = response.body()!!
                if (rs.success == 1){
                    Glide.with(this@InfoKostActivity)
                        .load(R.drawable.ic_baseline_favorite_24)
                        .into(binding.imgFavorites)
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
                Toast.makeText(this@InfoKostActivity,t.message,Toast.LENGTH_LONG).show()
            }

        })
    }
}