package com.example.kostuty.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isEmpty
import com.example.kostuty.Api.ApiConfig
import com.example.kostuty.Model.UserModel
import com.example.kostuty.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener{
            register()
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
        binding.txtLogin.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }
    private fun register() {
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
        ApiConfig.instance.register(
            binding.edtUsername.editText?.text.toString(),
            binding.edtTelepon.editText?.text.toString(),
            binding.edtEmail.editText?.text.toString(),
            binding.edtPassword.editText?.text.toString()
        ).enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                Toast.makeText(this@RegisterActivity,response.body()!!.message,Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Toast.makeText(this@RegisterActivity,"GAGAL REGISTER = " + t.toString(),Toast.LENGTH_SHORT).show()
            }

        })
    }
}