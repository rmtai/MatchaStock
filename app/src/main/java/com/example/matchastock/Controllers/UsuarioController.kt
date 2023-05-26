package com.example.matchastock.Controllers

import com.example.matchastock.Entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.text.Normalizer.Form

class UsuarioController(private val client: OkHttpClient) {

    companion object {
        private const val URL_API = "http://192.168.0.11/MatchaStock/Usuario/"
        private const val INSERTAR_URL = "${URL_API}insertar.php"
        private const val EDITAR_URL = "${URL_API}editar.php"
        private const val MOSTRAR_URL = "${URL_API}mostrar.php"
    }

    fun agregarUsuario(usuario: User) {
        val formBody: FormBody = FormBody.Builder()
            .add("nombre", usuario.nombre)
            .add("apellido", usuario.apellido)
            .add("username", usuario.username)
            .add("passwordUser", usuario.passwordUser)
            .add("email", usuario.email)
            .build()

        val request: Request = Request.Builder()
            .url(INSERTAR_URL)
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Error en la petición HTTP: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val respuesta = response.body?.string()
                    println(respuesta)
                    response.close()

                } else {
                    println("Error en la respuesta del servidor")
                }
            }
        })
    }

    fun editarUsuario(nombre: String, apellido: String, username: String, passwordUser: String) {
        val formBody = FormBody.Builder()
            .add("nombre", nombre)
            .add("apellido", apellido)
            .add("username", username)
            .add("passwordUser", passwordUser)
            .build()

        val request: Request = Request.Builder()
            .url(EDITAR_URL)
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Error en la petición HTTP: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val respuesta = response.body?.string()
                    println(respuesta)
                    response.close()
                } else {
                    println("Error en la respuesta del servidor")
                }
            }
        })
    }

    suspend fun mostrarUsuarios(): List<User> = withContext(Dispatchers.IO) {
        val usuarios = mutableListOf<User>()

        val request = Request.Builder()
            .url(MOSTRAR_URL)
            .build()

        val response = client.newCall(request).await()

        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }

        val json = response.body!!.string()
        val jsonArray = JSONArray(json)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val idUsuario = jsonObject.getInt("idUser")
            val nombre = jsonObject.getString("nombre")
            val apellido = jsonObject.getString("apellido")
            val usuario = jsonObject.getString("username")
            val password = jsonObject.getString("passwordUser")
            val email = jsonObject.getString("email")

            usuarios.add(User(idUsuario, nombre, apellido, usuario, password, email))
        }

        usuarios
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
