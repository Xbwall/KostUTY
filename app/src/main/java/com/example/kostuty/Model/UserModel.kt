package com.example.kostuty.Model

import com.google.gson.annotations.SerializedName

data class UserModel(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("telepon")
	val telepon: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("Success")
	val success: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)
