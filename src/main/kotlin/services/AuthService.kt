package services
import com.google.gson.Gson
import java.io.File
import models.User
import java.util.UUID

public class AuthService{
    var users = mutableListOf<User>()
    var nextId = UUID.randomUUID().toString()
    fun registerUser(name: String, password: String): User {
        val user = User(nextId,name, password)
        users.add(user)
        return user
    }
    fun getUserById(id: String): User? {
        return users.find { it.userId == id }
    }
    fun getUserByUsername(name: String): User? {
        return users.find { it.name == name }
    }
    fun authenticate(name: String, password: String): Boolean {
        val user = users.find { it.name == name && it.password == password }
        return user != null
    }
    // Save user into a Json File
    fun saveUsersInJson() {
        val gson = Gson()
        val json = gson.toJson(users)
        File("users.json").writeText(json)
    }
    fun saveSingleUserInJson(user: User) {
        val gson = Gson()
        val json = File("users.json").readText()
        val users = gson.fromJson(json, Array<User>::class.java).toMutableList()
        users.add(user)
        val updatedJson = gson.toJson(users)
        File("users.json").writeText(updatedJson)
    }
    // Load users from a Json File
    fun loadUsersFromJson() {
        val gson = Gson()
        val json = File("users.json").readText()
        val users = gson.fromJson(json, Array<User>::class.java)
        this.users = users.toMutableList()
    }
    fun signIn(authService: AuthService): Pair<String, Boolean> {
        println("\nSign in to Blogging Platform App")
        print("\nEnter your username: ")
        val username = readln()
        print("Enter your password: ")
        val password = readln()
        val user = authService.getUserByUsername(username)
        return if (user != null) {
            Pair(user.userId, authService.authenticate(username, password))
        } else {
            Pair("", false)
        }
    }
    fun register(authService: AuthService): Pair<String, Boolean> {
        println("\nCreate Account for Blogging Platform App")  
        print("\nEnter your username: ")
        val username = readln()
        print("Enter your password: ")
        val password = readln()
        val user = authService.registerUser(username, password)
        authService.saveSingleUserInJson(user)
        return Pair(user.userId, authService.authenticate(username, password))
    }
}