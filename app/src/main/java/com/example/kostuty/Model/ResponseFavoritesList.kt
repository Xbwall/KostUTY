package com.example.kostuty.Model

import com.google.gson.annotations.SerializedName

data class ResponseFavoritesList(

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("gambar_kost")
	val gambarKost: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("jarak")
	val jarak: String? = null,

	@field:SerializedName("nama_kost")
	val namaKost: String? = null,

	@field:SerializedName("kamar")
	val kamar: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("kategori")
	val kategori: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
