package com.example.matchastock.Entities

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("idItem") val idItem: Int?,
    @SerializedName("nombreProd") val nombreProd: String,
    @SerializedName("cantidadProd") val cantidadProd: Int?,
    @SerializedName("estado") val estado: Int,
    @SerializedName("descripcionProd") val descripcionProd: String,
    @SerializedName("imagen") val imagen : ByteArray?,
    @SerializedName("idUser") val idUser: Int?


)
