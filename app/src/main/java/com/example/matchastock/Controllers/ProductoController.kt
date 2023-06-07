package com.example.matchastock.Controllers

import android.util.Base64
import com.example.matchastock.Entities.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ProductoController(private val client: OkHttpClient) {

    interface OnProductoGuardadoListener {
        fun onProductoGuardadoExitosamente()
        fun onErrorGuardado(mensaje: String)
    }

    interface OnProductoObtenidoListener {
        fun onProductoObtenidoExitosamente()
        fun onErrorObtenido(mensaje: String)
        fun onProductoObtenido(productos: List<Product>)
    }



    companion object {
        private const val URL_API = "http://192.168.0.12/MatchaStock/Producto/"
        private const val INSERTAR_URL = "${URL_API}insertar.php"
        private const val EDITAR_URL = "${URL_API}editar.php"
        private const val MOSTRAR_URL = "${URL_API}mostrar.php"
        private const val PRODEL_URL = "${URL_API}getProd.php"

    }
    fun agregarProducto(producto: Product) {


        if (producto.nombreProd.isNullOrEmpty() || producto.descripcionProd.isNullOrEmpty() || producto.cantidadProd!! <= 0) {
            println("Ingrese valores válidos para todos los campos del producto")
            return
        }
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("nombreProd", producto.nombreProd)
        builder.addFormDataPart("descripcionProd", producto.descripcionProd)
        builder.addFormDataPart("cantidadProd", producto.cantidadProd.toString())
        builder.addFormDataPart("idUser", producto.idUser.toString())


        val requestBody = builder.build()

        val request: Request = Request.Builder()
            .url(INSERTAR_URL)
            .post(requestBody)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    val respuesta = response.body?.string()
                    println(respuesta)
                }
                else {
                    println("Error en la respuesta del servidor")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Error en la petición HTTP: ${e.message}")
            }
        })
    }

    fun editarProducto(producto: Product) {

        if (producto.nombreProd.isNullOrEmpty() || producto.descripcionProd.isNullOrEmpty() || producto.cantidadProd!! <= 0 ) {
            println("Ingrese valores válidos para todos los campos del producto")
            return
        }

        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("idItem", producto.idItem.toString())
        builder.addFormDataPart("nombreProd", producto.nombreProd)
        builder.addFormDataPart("descripcionProd", producto.descripcionProd)
        builder.addFormDataPart("cantidadProd", producto.cantidadProd.toString())
        builder.addFormDataPart("estado", producto.estado.toString())
        builder.addFormDataPart("idUser", producto.idUser.toString())


        val requestBody = builder.build()

        val request: Request = Request.Builder()
            .url(EDITAR_URL)
            .post(requestBody)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    val respuesta = response.body?.string()
                    println(respuesta)
                }
                else {
                    println("Error en la respuesta del servidor")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Error en la petición HTTP: ${e.message}")
            }
        })
    }



    fun mostrarProductosEliminados(): List<Product> = runBlocking {
        val productos = mutableListOf<Product>()

        launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url("$PRODEL_URL?estado=3") // Agrega el parámetro de estado en la URL
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }

                val json = response.body!!.string()
                val jsonArray = JSONArray(json)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    val idItem = jsonObject.getInt("idItem")
                    val nombreProd = jsonObject.getString("nombreProd")
                    val descripcionProd = jsonObject.getString("descripcionProd")
                    val cantidadProd = jsonObject.getInt("cantidadProd")
                    val estado = jsonObject.getInt("estado")
                    val idUser = jsonObject.getInt("idUser")

                    productos.add(
                        Product(
                            idItem,
                            nombreProd,
                            descripcionProd,
                            cantidadProd,
                            estado,
                            idUser
                        )
                    )
                }
            }
        }.join()

        productos
    }

    fun mostrarProducto(): List<Product> = runBlocking {
        val productos = mutableListOf<Product>()

        launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url(MOSTRAR_URL)
                .build()

            val client = OkHttpClient()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }

                val json = response.body!!.string()
                val jsonArray = JSONArray(json)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    val idItem = jsonObject.getInt("idItem")
                    val nombreProd = jsonObject.getString("nombreProd")
                    val descripcionProd = jsonObject.getString("descripcionProd")
                    val cantidadProd = jsonObject.getInt("cantidadProd")
                    val estado = jsonObject.getInt("estado")
                    val idUser = jsonObject.getInt("idUser")


                    productos.add(Product(idItem, nombreProd, descripcionProd, cantidadProd, estado, idUser))
                }
            }
        }.join()

        productos
    }
    fun mostrarTodosLosProductos() {
        val request = Request.Builder()
            .url(MOSTRAR_URL)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val errorMessage = "Error en la petición HTTP: ${e.message}"
                println(errorMessage)

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val respuesta = response.body?.string()
                    val jsonArray = JSONArray(respuesta)

                    val productos = mutableListOf<Product>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        val idItem = jsonObject.getInt("idItem")
                        val nombreProd = jsonObject.getString("nombreProd")
                        val descripcionProd = jsonObject.getString("descripcionProd")
                        val cantidadProd = jsonObject.getInt("cantidadProd")
                        val estado = jsonObject.getInt("estado")
                        val idUser = jsonObject.getInt("idUser")

                        productos.add(Product(idItem, nombreProd, descripcionProd, cantidadProd, estado, idUser))
                    }

                  productos
                } else {
                    val error = "Error en la respuesta del servidor"
                    println(error)
                }

                response.close()
            }
        })
    }

}


