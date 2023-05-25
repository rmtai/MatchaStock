package com.example.matchastock.Controllers

import com.example.matchastock.Entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class UsuarioController {

    fun agregarUsuario(usuario: User) {


        val urlAPI = "http://192.168.0.11/MatchaStock/Usuario/insertar.php"

        val client = OkHttpClient()

        val requestBody: RequestBody = FormBody.Builder()
            .add("nombre", usuario.nombre)
            .add("apellido", usuario.apellido)
            .add("username", usuario.username)
            .add("passwordUser", usuario.passwordUser)
            .add("email", usuario.email)
            .build()

        val request: Request = Request.Builder()
            .url(urlAPI)
            .post(requestBody).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Error en la petición HTTP: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val respuesta = response.body?.string()
                    println(respuesta)
                } else {
                    println("Error en la respuesta del servidor")
                }
            }

        })
    }

    fun editarUsuario(nombre: String, apellido: String, username: String, passwordUser: String) {

        val urlAPI = "http://192.168.0.11/MatchaStock/Usuario/insertar.php"

        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("nombre", nombre)
            .add("apellido", apellido)
            .add("username", username)
            .add("passwordUser", passwordUser)
            .build()

        val request: Request = Request.Builder()
            .url(urlAPI)
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
                } else {
                    println("Error en la respuesta del servidor")
                }
            }

        })
    }

    fun mostrarUsuarios(): List<User> = runBlocking {
        val usuarios = mutableListOf<User>()
        val urlAPI = "http://192.168.0.11/MatchaStock/Usuario/mostrar.php"

        launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url(urlAPI)
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

                    val idUsuario = jsonObject.getInt("idUser")
                    val nombre = jsonObject.getString("nombre")
                    val apellido = jsonObject.getString("apellido")
                    val usuario = jsonObject.getString("username")
                    val password = jsonObject.getString("passwordUser")
                    val email = jsonObject.getString("email")

                    usuarios.add(User(idUsuario,nombre, apellido, usuario, password, email))

                }
            }
        }.join()
        usuarios
    }
}