package com.example.kostuty.Kost

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.kostuty.Api.ApiConfig
import com.example.kostuty.Helper.Constant
import com.example.kostuty.Helper.GetFileProperties
import com.example.kostuty.Helper.SharePrefHelper
import com.example.kostuty.Model.ResponseCUD
import com.example.kostuty.Model.ResponseDetailKost
import com.example.kostuty.R
import com.example.kostuty.beranda.HomeActivity
import com.example.kostuty.databinding.ActivityInfoKostPenjualBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class InfoKostPenjual : AppCompatActivity() {

    private lateinit var filePart: MultipartBody.Part
    private lateinit var binding: ActivityInfoKostPenjualBinding
    lateinit var sharePref: SharePrefHelper
    private var selectedImageUri: Uri? = null
    private var path: String? = null
    private var id_usr: String? = null
    private var id_kost: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoKostPenjualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharePref = SharePrefHelper(this)
        id_kost = sharePref.getString(Constant.PREF_ID_KOST)
        id_usr = sharePref.getString(Constant.PREF_ID)
        binding.imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        binding.btnKembali.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

        if(id_kost != null){
            ApiConfig.instance.detailkost(id_kost.toString().toInt()).enqueue(object : Callback<ResponseDetailKost>{
                override fun onResponse(
                    call: Call<ResponseDetailKost>,
                    response: Response<ResponseDetailKost>
                ) {
                    val res = response.body()!!
                    Glide.with(this@InfoKostPenjual)
                        .load(ApiConfig.base_url+res.gambarKost)
                        .centerCrop()
                        .placeholder(R.drawable.noimage)
                        .into(binding.imageView)
                    binding.edtNamakosupdate.editText?.setText(res.namaKost.toString())
                    binding.edtAlamatkostupdate.editText?.setText(res.alamat.toString())
                    binding.edtJarakupdate.editText?.setText(res.jarak.toString())
                    binding.edtKategorikostupdate.editText?.setText(res.kategori.toString())
                    binding.edtJumahkamarupdate.editText?.setText(res.kamar.toString())
                    binding.edtHargakostupdate.editText?.setText(res.harga.toString())
                    binding.edtDesripsikostupdate.editText?.setText(res.deskripsi.toString())
                }

                override fun onFailure(call: Call<ResponseDetailKost>, t: Throwable) {
                    Log.e("ERROR", t.message.toString())
                }

            })
        }

        binding.btnSaveinfokost.setOnClickListener {
            Log.e("URI"," Path : "+path)
            if (id_kost == "0"){
                //insert
                insert()
            }else{
                //update
                update()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100){
            selectedImageUri = data?.data
            binding.imageView.setImageURI(selectedImageUri)
//            path = selectedImageUri?.let {
//                GetFileProperties.getFilePath(applicationContext,
//                    it
//                )
//            }
            path = selectedImageUri?.let { it1 -> applicationContext?.let { it2 -> GetFileProperties.getFilePath(it2, it1) } }
            Log.e("URI"," Path : "+path)
        }
    }

    fun insert(){
        val emptyFile = MultipartBody.Part.createFormData("file", null.toString())

        if (path == null){
            filePart = emptyFile
        }else{
            val file = File(path)
            val fileRequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val notEmptyFile = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)
            filePart = notEmptyFile
        }

        //namakost
        val namekost = binding.edtNamakosupdate.editText?.text.toString()
        val namaKostRequestBody = namekost.toRequestBody("text/plain".toMediaTypeOrNull())

        val alamat = binding.edtAlamatkostupdate.editText?.text.toString()
        val alamatRequestBody = alamat.toRequestBody("text/plain".toMediaTypeOrNull())

        val jarak = binding.edtJarakupdate.editText?.text.toString()
        val jarakRequestBody = jarak.toRequestBody("text/plain".toMediaTypeOrNull())

        val kategori = binding.edtKategorikostupdate.editText?.text.toString()
        val kategoriRequestBody = kategori.toRequestBody("text/plain".toMediaTypeOrNull())

        val jml_kmr = binding.edtJumahkamarupdate.editText?.text.toString()
        val jml_kmrRequestBody = jml_kmr.toRequestBody("text/plain".toMediaTypeOrNull())

        val harga = binding.edtHargakostupdate.editText?.text.toString()
        val hargaRequestBody = harga.toRequestBody("text/plain".toMediaTypeOrNull())

        val diskripsi = binding.edtDesripsikostupdate.editText?.text.toString()
        val diskripsiRequestBody = diskripsi.toRequestBody("text/plain".toMediaTypeOrNull())

        val id_user = id_usr
        val id_userRequestBody = id_user!!.toRequestBody("text/plain".toMediaTypeOrNull())

        val loading = ProgressDialog(this)
        loading.setMessage("Loading...")
        loading.setCancelable(false)
        loading.show()
        // init retrofit
        ApiConfig.instance.submitKost(namaKostRequestBody,alamatRequestBody,jarakRequestBody,
            hargaRequestBody,diskripsiRequestBody,kategoriRequestBody,
            jml_kmrRequestBody,id_userRequestBody,filePart).enqueue(object : Callback<ResponseCUD>{
            override fun onResponse(call: Call<ResponseCUD>, response: Response<ResponseCUD>) {
                val res = response.body()!!
                startActivity(Intent(this@InfoKostPenjual,DaftarKostActivity::class.java))
                finish()
                Toast.makeText(applicationContext,res.message,Toast.LENGTH_SHORT).show()
                sharePref.put(Constant.PREF_ID_KOST,"0")
                loading.hide()
            }

            override fun onFailure(call: Call<ResponseCUD>, t: Throwable) {
                sharePref.put(Constant.PREF_ID_KOST,"")
                Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                Log.e("ERROR_insert", t.message.toString())
                loading.hide()
            }

        })
    }

    fun update(){


        val emptyFile = MultipartBody.Part.createFormData("file", null.toString())

        if (path != null){
            val file = File(path)
            val fileRequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val notEmptyFile = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)
            filePart = notEmptyFile
        }else{
            filePart = emptyFile
        }

        val idKostRequestBody = id_kost!!.toRequestBody("text/plain".toMediaTypeOrNull())

        //namakost
        val namekost = binding.edtNamakosupdate.editText?.text.toString()
        val namaKostRequestBody = namekost.toRequestBody("text/plain".toMediaTypeOrNull())

        val alamat = binding.edtAlamatkostupdate.editText?.text.toString()
        val alamatRequestBody = alamat.toRequestBody("text/plain".toMediaTypeOrNull())

        val jarak = binding.edtJarakupdate.editText?.text.toString()
        val jarakRequestBody = jarak.toRequestBody("text/plain".toMediaTypeOrNull())

        val kategori = binding.edtKategorikostupdate.editText?.text.toString()
        val kategoriRequestBody = kategori.toRequestBody("text/plain".toMediaTypeOrNull())

        val jml_kmr = binding.edtJumahkamarupdate.editText?.text.toString()
        val jml_kmrRequestBody = jml_kmr.toRequestBody("text/plain".toMediaTypeOrNull())

        val harga = binding.edtHargakostupdate.editText?.text.toString()
        val hargaRequestBody = harga.toRequestBody("text/plain".toMediaTypeOrNull())

        val diskripsi = binding.edtDesripsikostupdate.editText?.text.toString()
        val diskripsiRequestBody = diskripsi.toRequestBody("text/plain".toMediaTypeOrNull())

        val id_user = id_usr
        val id_userRequestBody = id_user!!.toRequestBody("text/plain".toMediaTypeOrNull())

        val loading = ProgressDialog(this)
        loading.setMessage("Loading...")
        loading.setCancelable(false)
        loading.show()
        // init retrofit
        ApiConfig.instance.updateKost(idKostRequestBody,namaKostRequestBody,alamatRequestBody,jarakRequestBody,
            hargaRequestBody,diskripsiRequestBody,kategoriRequestBody,
            jml_kmrRequestBody,id_userRequestBody,filePart).enqueue(object : Callback<ResponseCUD>{
            override fun onResponse(call: Call<ResponseCUD>, response: Response<ResponseCUD>) {
                val res = response.body()!!
                startActivity(Intent(this@InfoKostPenjual,DaftarKostActivity::class.java))
                finish()
                Toast.makeText(applicationContext,res.message,Toast.LENGTH_SHORT).show()
                sharePref.put(Constant.PREF_ID_KOST,"0")
                loading.hide()
            }

            override fun onFailure(call: Call<ResponseCUD>, t: Throwable) {
                sharePref.put(Constant.PREF_ID_KOST,"")
                Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                Log.e("ERROR_update", t.message.toString())
                loading.hide()
            }

        })
    }
}