package services
import com.google.gson.Gson
import java.io.File
import models.User
import java.util.UUID

public class AuthService{
    // Add a list of users and a nextId property to the AuthService class
    var users = mutableListOf<User>()
    // Add a nextId property to the AuthService class to generate unique user IDs
    var nextId = UUID.randomUUID().toString()
    // Add a registerUser method to the AuthService class to create a new user
    fun registerUser(name: String, password: String): User {
        val user = User(nextId,name, password)
        users.add(user)
        return user
    }
    // Add a getUserById method to the AuthService class to find a user by ID
    fun getUserById(id: String): User? {
        return users.find { it.userId == id }
    }
    // Add a getUserByUsername method to the AuthService class to find a user by username
    fun getUserByUsername(name: String): User? {
        return users.find { it.name == name }
    }
    // Add an authenticate method to the AuthService class to verify a user's credentials
    fun authenticate(name: String, password: String): Boolean {
        val user = users.find { it.name == name && it.password == password }
        return user != null
    }
    // Add a saveUsersInJson method to the AuthService class to save users to a Json file
    fun saveUsersInJson() {
        val gson = Gson()
        val json = gson.toJson(users)
        File("users.json").writeText(json)
    }
    // Add a saveSingleUserInJson method to the AuthService class to save a single user to a Json file
    fun saveSingleUserInJson(user: User) {
        val gson = Gson()
        val json = File("users.json").readText()
        val users = gson.fromJson(json, Array<User>::class.java).toMutableList()
        users.add(user)
        val updatedJson = gson.toJson(users)
        File("users.json").writeText(updatedJson)
    }
    // Add a loadUsersFromJson method to the AuthService class to load users from a Json file
    fun loadUsersFromJson() {
        val gson = Gson()
        val json = File("users.json").readText()
        val users = gson.fromJson(json, Array<User>::class.java)
        this.users = users.toMutableList()
    }
    // Add a signIn method to the AuthService class to sign in a user and return their ID and a boolean indicating success or failure
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
    // Add a register method to the AuthService class to register a new user and return their ID and a boolean indicating success or failure
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