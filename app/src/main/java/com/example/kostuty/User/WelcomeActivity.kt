package com.example.kostuty.User

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.example.kostuty.Api.ApiConfig
import com.example.kostuty.Helper.Constant
import com.example.kostuty.Helper.SharePrefHelper
import com.example.kostuty.Model.UserModel
import com.example.kostuty.beranda.HomeActivity
import com.example.kostuty.databinding.ActivityWelcomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    lateinit var sharePref: SharePrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharePref = SharePrefHelper(this@WelcomeActivity)

        binding.txtRegister.setOnClickListener({
            startActivity(Intent(this,RegisterActivity::class.java))
        })
        binding.btnLogin.setOnClickListener({
            login()
        })
    }

    private fun login() {
        if (binding.edtEmail.editText?.text?.isEmpty() == true){
            binding.edtEmail.error = "Kolom Email Tidak boleh Kosong"
            binding.edtEmail.requestFocus()
            return
        }
        if (binding.edtPassword.editText?.text?.isEmpty() == true){
            binding.edtPassword.error = "Kolom Password Tidak boleh Kosong"
            binding.edtPassword.requestFocus()
            return
        }
        val loading = ProgressDialog(this)
        loading.setMessage("Loading, please wait...")
        loading.show()
            ApiConfig.instance.login(binding.edtEmail.editText?.text.toString(), binding.edtPassword.editText?.text.toString()).enqueue(object : Callback<UserModel>{
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    val respon = response.body()!!
                    if(respon.success == 1){
                        loading.hide()
                        sharePref.put(Constant.PREF_ID,respon.idUser.toString())
                        sharePref.put(Constant.PREF_NAMA,respon.nama.toString())
                        sharePref.put(Constant.PREF_EMAIL,respon.email.toString())
                        sharePref.put(Constant.PREF_TELEPON,respon.telepon.toString())
                        startActivity(Intent(this@WelcomeActivity,HomeActivity::class.java))
                    }else{
                        loading.hide()
                        Toast.makeText(this@WelcomeActivity,respon.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    loading.hide()
                    Log.e("ERROR","============"+t.toString())
                }
            })
    }


}