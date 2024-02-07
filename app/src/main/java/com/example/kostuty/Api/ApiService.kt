package com.example.kostuty.Api

import com.example.kostuty.Model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

//=====================UserSERVICE========================//
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserModel>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("nama") nama: String,
        @Field("telepon") telepon: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserModel>

    @FormUrlEncoded
    @POST("updateprof")
    fun update(
        @Field("id") id:Int,
        @Field("nama") nama: String,
        @Field("telepon") telepon: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserModel>

    @FormUrlEncoded
    @POST("cekfavorite")
    fun cekfavorite(
        @Field("id_kost") id_kost:Int,
        @Field("id_user") id_usr:Int,
    ): Call<UserModel>

    @FormUrlEncoded
    @POST("favorites")
    fun favorite(
        @Field("id_kost") id_kost:Int,
        @Field("id_user") id_usr:Int,
    ): Call<UserModel>

    @GET("listfavorites/{id}")
    fun listFavorites(
        @Path("id") id:String
    ):Call<ArrayList<ResponseFavoritesList>>

//=====================KostSERVICE========================//
    @FormUrlEncoded
    @POST("kostuser")
    fun kostuser(
        @Field("id") id:Int,
    ): Call<ArrayList<KostModel>>

    @FormUrlEncoded
    @POST("kostdetail")
    fun detailkost(
        @Field("id") id:Int
    ): Call<ResponseDetailKost>

    @FormUrlEncoded
    @POST("carikost")
    fun carikost(
        @Field("cari") cari:String
    ):Call<ArrayList<ResponseHomeList>>

    @POST("deletekost/{id}")
    fun deletekost(
        @Path("id") id:Int
    ):Call<ResponseCUD>

    @Multipart
    @POST("registerkost")
    fun submitKost(
        @Part("nama_kost") namaKost: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("jarak") jarak: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("kategori") kategori: RequestBody,
        @Part("kamar") kamar: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<ResponseCUD>

    @Multipart
    @POST("updatekost")
    fun updateKost(
        @Part("id") idKost : RequestBody,
        @Part("nama_kost") namaKost: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("jarak") jarak: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("kategori") kategori: RequestBody,
        @Part("kamar") kamar: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<ResponseCUD>
}