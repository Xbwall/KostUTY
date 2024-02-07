package com.example.kostuty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.kostuty.Api.ApiConfig
import com.example.kostuty.Helper.Constant
import com.example.kostuty.Helper.SharePrefHelper
import com.example.kostuty.Model.UserModel
import com.example.kostuty.beranda.HomeActivity
import com.example.kostuty.databinding.ActivityUpdateProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateProfileBinding
    lateinit var sharepref: SharePrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharepref = SharePrefHelper(this)
        var id = sharepref.getString(Constant.PREF_ID)

        binding.btnSimpan.setOnClickListener({
            updateprofile(id.toString().toInt())

        })
    }

    private fun updateprofile(id: Int) {
        if (binding.edtUsername.editText?.text?.isEmpty() == true){
            binding.edtUsername.error = "Kolom Username Tidak boleh Kosong"
            binding.edtUsername.requestFocus()
            return
        }
        if (binding.edtEmail.editText?.text?.isEmpty() == true){
            binding.edtEmail.error = "Kolom Email Tidak boleh Kosong"
            binding.edtEmail.requestFocus()
            return
        }
        if (binding.edtTelepon.editText?.text?.isEmpty() == true){
            binding.edtTelepon.error = "Kolom Telepon Tidak boleh Kosong"
            binding.edtTelepon.requestFocus()
            return
        }
        if (binding.edtPassword.editText?.text?.isEmpty() == true){
            binding.edtPassword.error = "Kolom Password Tidak boleh Kosong"
            binding.edtPassword.requestFocus()
            return
        }
        ApiConfig.instance.update(
            id,
            binding.edtUsername.editText?.text.toString(),
            binding.edtTelepon.editText?.text.toString(),
            binding.edtEmail.editText?.text.toString(),
            binding.edtPassword.editText?.text.toString()
        ).enqueue(object : Callback<UserModel>{
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    startActivity(Intent(this@UpdateProfileActivity, HomeActivity::class.java))
                    Toast.makeText(this@UpdateProfileActivity,response.body()!!.message,Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Toast.makeText(this@UpdateProfileActivity,t.message.toString(),Toast.LENGTH_SHORT).show()
                    Log.e("ERROR","======="+t.message.toString())
                }

            })
    }


}