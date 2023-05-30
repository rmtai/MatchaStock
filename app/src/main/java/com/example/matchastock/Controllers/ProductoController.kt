package com.example.matchastock.Controllers

import android.util.Base64
import com.example.matchastock.Entities.Product
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ProductoController(private val client: OkHttpClient) {

    interface OnProductoGuardadoListener{
        fun onProductoGuardadoExitosamente()
        fun onErrorGuardado(mensaje: String)
    }

    companion object {
        private const val URL_API = "http://192.168.0.11/MatchaStock/Producto/"
        private const val INSERTAR_URL = "${URL_API}insertar.php"
        private const val EDITAR_URL = "${URL_API}editar.php"
        private const val MOSTRAR_URL = "${URL_API}mostrar.php"

    }
    fun guardarProducto(producto: Product, listener: OnProductoGuardadoListener){
        val imagenCodificada: String = Base64.encodeToString(producto.imagen, Base64.DEFAULT)


        val formBody: FormBody = FormBody.Builder()
            .add("nombreProd", producto.nombreProd)
            .add("descripcionProd", producto.descripcionProd)
            .add("cantidadProd", producto.cantidadProd.toString())
            .add("imagen", imagenCodificada)
            .add("idUser", producto.idUser.toString())
            .build()

        val request: Request = Request.Builder()
            .url(INSERTAR_URL)
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException){
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


        // Función de extensión para realizar una llamada asíncrona y obtener una respuesta
        private suspend fun Call.await(): Response {
            return suspendCancellableCoroutine { continuation ->
                enqueue(object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        continuation.resumeWith(Result.success(response))
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        continuation.resumeWith(Result.failure(e))
                    }
                })

                continuation.invokeOnCancellation {
                    try {
                        cancel()
                    } catch (ex: Throwable) {
                        // Ignorar la cancelación fallida
                    }
                }
            }
        }
    }


