package com.example.kostuty.Model

import com.google.gson.annotations.SerializedName

data class ResponseCUD(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Success")
	val success: Int? = null
)
