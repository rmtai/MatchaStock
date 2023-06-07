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

     fun guardarProducto(producto: Product, listener: OnProductoGuardadoListener) {

        val formBody: FormBody = FormBody.Builder()
            .add("nombreProd", producto.nombreProd)
            .add("descripcionProd", producto.descripcionProd)
            .add("cantidadProd", producto.cantidadProd.toString())
            .add("idUser", producto.idUser.toString())
            .build()

        val request: Request = Request.Builder()
            .url(INSERTAR_URL)
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val errorMessage = "Error en la petición HTTP: ${e.message}"
                listener.onErrorGuardado(errorMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val respuesta = response.body?.string()
                    val jsonResponse = JSONObject(respuesta)

                    if (jsonResponse.getBoolean("success")) {
                        listener.onProductoGuardadoExitosamente()
                    } else {
                        val mensaje = jsonResponse.getString("message")
                        listener.onErrorGuardado(mensaje)
                    }
                } else {
                    val error = "Error en la respuesta del servidor"
                    listener.onErrorGuardado(error)
                }

                response.close()
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

    fun mostrarTodosLosProductos(listener: OnProductoObtenidoListener) {
        val request = Request.Builder()
            .url(MOSTRAR_URL)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val errorMessage = "Error en la petición HTTP: ${e.message}"
                listener.onErrorObtenido(errorMessage)
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

                  listener.onProductoObtenido(productos)
                } else {
                    val error = "Error en la respuesta del servidor"
                    listener.onErrorObtenido(error)
                }

                response.close()
            }
        })
    }

}


