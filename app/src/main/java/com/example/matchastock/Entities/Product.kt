package com.example.matchastock.Entities

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("idItem") val idItem: Int?,
    @SerializedName("nombreProd") val nombreProd: String,
    @SerializedName("descripcionProd") val descripcionProd: String,
    @SerializedName("cantidadProd") val cantidadProd: Int?,
    @SerializedName("imagen") val imagen : ByteArray?,
    @SerializedName("estado") val estado: Int,
    @SerializedName("idUser") val idUser: Int?


)
