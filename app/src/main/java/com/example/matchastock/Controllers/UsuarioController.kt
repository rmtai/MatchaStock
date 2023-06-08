package com.example.matchastock.Controllers

import android.util.Log
import com.example.matchastock.Entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class UsuarioController(private val client: OkHttpClient) {


    interface OnUsuarioRegistradoListener {
        fun onUsuarioRegistradoExitosamente()
        fun onErrorRegistro(mensaje: String)
    }

    interface OnUsuarioLoginListener {
        fun onUsuarioLoginExitoso()
        fun onErrorLogin(mensaje: String)
    }

    companion object {
        private const val URL_API = "http://192.168.1.21/MatchaStock/Usuario/"
        private const val INSERTAR_URL = "${URL_API}insertar.php"
        private const val EDITAR_URL = "${URL_API}editar.php"
        private const val MOSTRAR_URL = "${URL_API}mostrar.php"
        private const val LOGIN_URL = "${URL_API}login.php"
        private const val ELIMINAR_URL = "${URL_API}eliminar.php"

    }

    fun agregarUsuario(usuario: User, listener: OnUsuarioRegistradoListener) {
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
                    listener.onUsuarioRegistradoExitosamente()
                } else {
                    val error = "Error en la respuesta del servidor"
                    println(error)
                    listener.onErrorRegistro(error)
                }
            }
        })
    }

    fun editarUsuario(idUser: Int, passwordUser: String){

        val formBody = FormBody.Builder()
            .add("idUser", idUser.toString())
            .add("passwordUser", passwordUser)
            .build()
        val request = Request.Builder()
            .url(EDITAR_URL)
            .post(formBody)
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

    fun mostrarUsuario(): List<User> = runBlocking {
        val usuarios = mutableListOf<User>()



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

                    val idUser = jsonObject.getInt("idUser")
                    val nombre = jsonObject.getString("nombre")
                    val apellido = jsonObject.getString("apellido")
                    val username = jsonObject.getString("username")
                    val passwordUser = jsonObject.getString("passwordUser")
                    val email = jsonObject.getString("email")


                    usuarios.add(User(idUser, nombre, apellido, username, passwordUser, email))
                }
            }
        }.join()

        usuarios
    }

    fun eliminarUsuario(usuario:User){

        val formBody = FormBody.Builder()
            .add("idUser", usuario.idUser.toString())
            .build()
        val request = Request.Builder()
            .url(ELIMINAR_URL)
            .post(formBody)
            .build()
        val client = OkHttpClient()


        client.newCall(request).enqueue(object: Callback {
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

    fun validarUsuario(usuario: String, clave: String):User?{
        val lista = mostrarUsuario()
        var usuarioAux = User(0, "", " ", "", "", "")
        lista.forEach {
            if(it.username.trim() == usuario.trim() && it.passwordUser.trim() == clave.trim()){
                usuarioAux = it
            }
        }
        if(usuarioAux.idUser == null){
            Log.d("Sesion", "sesion fallida")
        }
        return usuarioAux
    }

    fun login(username: String, passwordUser: String, listener: OnUsuarioLoginListener) {

        val formBody = FormBody.Builder()
            .add("username", username)
            .add("passwordUser", passwordUser)
            .build()

        val request: Request = Request.Builder()
            .url(LOGIN_URL)
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Error en la petición HTTP
                val errorMessage = "Error en la petición HTTP: ${e.message}"
                println(errorMessage)
                listener.onErrorLogin(errorMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                if (response.isSuccessful && responseData != null) {
                    try {
                        val jsonResponse = JSONObject(responseData)
                        val success = jsonResponse.getBoolean("success")
                        val message = jsonResponse.getString("message")

                        if (success) {
                            // Inicio de sesión exitoso
                            listener.onUsuarioLoginExitoso()
                        } else {
                            // Contraseña incorrecta o usuario no encontrado
                            println("Error de inicio de sesión: $message")
                            listener.onErrorLogin(message)
                        }
                    } catch (e: JSONException) {
                        // Error al analizar el JSON
                        val errorMessage = "Error al analizar la respuesta del servidor: ${e.message}"
                        println(errorMessage)
                        listener.onErrorLogin(errorMessage)
                    }
                } else {
                    // Error en la respuesta del servidor
                    val errorMessage = "Error en la respuesta del servidor"
                    println(errorMessage)
                    listener.onErrorLogin(errorMessage)
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
