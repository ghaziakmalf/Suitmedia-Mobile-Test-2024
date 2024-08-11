package com.example.suitmediamobiletest2024.data.repository

import com.example.suitmediamobiletest2024.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class UserRepository {
    suspend fun fetchUsers(page: Int, perPage: Int): List<User> = withContext(Dispatchers.IO) {
        val url = URL("https://reqres.in/api/users?page=$page&per_page=$perPage")
        val connection = url.openConnection() as HttpURLConnection
        val users = mutableListOf<User>()

        connection.inputStream.bufferedReader().use { reader ->
            val response = reader.readText()
            val jsonObject = JSONObject(response)
            val data = jsonObject.getJSONArray("data")

            for (i in 0 until data.length()) {
                val userObject = data.getJSONObject(i)
                users.add(
                    User(
                        id = userObject.getInt("id"),
                        email = userObject.getString("email"),
                        firstName = userObject.getString("first_name"),
                        lastName = userObject.getString("last_name"),
                        avatar = userObject.getString("avatar")
                    )
                )
            }
        }
        users
    }
}
