import services.AuthService
import services.BlogService

fun main() {
    val authService = AuthService()
    val blogService = BlogService()
    // Load users from a Json File
    authService.loadUsersFromJson()

    // Global Variables for initialising the user
    var authenticated: Boolean = false
    var username: String = ""
    var currentUserId: String = ""

    try {
        // While loop to keep the user in the menu until authenticated
        while(!authenticated) {
            // Menu for Sign in and Register
            println("\nWelcome to Blogging Platform App")
            println("\n1. Sign in")
            println("2. Register")
            print("Enter your choice: ")
            val loginChoice = readln().toInt()
            when (loginChoice) {
                // Sign in to the app and get the user id and authentication status
                1 -> {
                    val (userId, auth) = authService.signIn(authService)
                    currentUserId = userId
                    authenticated = auth
                    if (auth) {
                        val user = authService.getUserById(currentUserId)
                        if (user != null) {
                            username = user.name
                        }
                    } else {
                        println("Invalid username or password")
                        Thread.sleep(2000)
                    }
                }
                // Register to the app and get the user id and authentication status
                2 -> {
                    val (userId, auth) = authService.register(authService)
                    currentUserId = userId
                    authenticated = auth
                    if (auth) {
                        val user = authService.getUserById(currentUserId)
                        if (user != null) {
                            username = user.name
                        }
                    }
                }
                else -> println("Invalid choice")
            }
        }
        // If authenticated, then display the menu for the user to create, publish, unpublish, update, delete and display all blogs
        if (authenticated) {
            println("\n--------- Welcome $username ----------")
            /* Menu for Blogs */
            while (true) {
                println("\n Blogging Platform Menu:")
                println("1. Create a blog")
                println("2. Publish a blog")
                println("3. Unpublish a blog")
                println("4. Update a blog")
                println("5. Delete a blog")
                println("6. Display all blogs")
                println("7. Exit")
                println("-----------------------------")
                print("Enter your choice: ")
                val choice = readln().toInt()
                when (choice) {
                    1 -> {
                        println("\nCreate a blog:")
                        print("\nEnter the title: ")
                        val title = readln()
                        print("Enter the content: ")
                        val content = readln()
                        blogService.createPost(currentUserId, title, content)
                        println("Post created.")
                        Thread.sleep(2000)
                    }
                    2 -> {
                        val userPosts = blogService.getUnpublishedPostsByUser(currentUserId)
                        if (userPosts.isEmpty()) {
                            println("\nYou have no unpublished blogs.")
                            print("\nEnter 0 to return to the main menu: ")
                            val option = readln().toInt()
                            if (option == 0) {
                                continue
                            }
                        }
                        println("\nYour Blogs:\n")
                        userPosts.forEachIndexed { index, post ->
                            println("${index + 1}. ${post.title}")
                        }
                        print(
                                "\nEnter the number of the blog to publish or enter 0 to return to the main menu: "
                        )
                        val postNumber = readln().toInt()
                        if (postNumber in 1..userPosts.size) {
                            val postToPublish = userPosts[postNumber - 1]
                            blogService.publishPost(currentUserId, postToPublish.id)
                            println("Blog published.")
                            Thread.sleep(2000)
                        } else if (postNumber == 0) {
                            continue
                        } else {
                            println("Invalid blog number.")
                        }
                    }
                    3 -> {
                        val posts = blogService.getPublishedPostsByUser(currentUserId)
                        if (posts.isEmpty()) {
                            println("\nYou have no published blogs.")
                            print("\nEnter 0 to return to the main menu: ")
                            val option = readln().toInt()
                            if (option == 0) {
                                continue
                            }
                        }
                        println("\nYour Blogs:\n")
                        posts.forEachIndexed { index, post ->
                            println("${index + 1}. ${post.title}")
                        }
                        print(
                                "\nEnter the number of the blog to unpublish or enter 0 to return to the main menu: "
                        )
                        val unpublishedPostNumber = readln().toInt()
                        if (unpublishedPostNumber == 0) {
                            continue
                        } else if (unpublishedPostNumber in 1..posts.size) {
                            val postToUnpublish = posts[unpublishedPostNumber - 1]
                            blogService.unpublishPost(currentUserId, postToUnpublish.id)
                            println("Blog unpublished.")
                            Thread.sleep(2000)
                        } else {
                            println("Invalid Blog number.")
                        }
                    }
                    4 -> {
                        val posts = blogService.getPostsByUser(currentUserId)
                        if (posts.isEmpty()) {
                            println("\nYou have no blogs.")
                            print("\nEnter 0 to return to the main menu: ")
                            val option = readln().toInt()
                            if (option == 0) {
                                continue
                            }
                        }
                        println("\nYour blogs:\n")
                        posts.forEachIndexed { index, post ->
                            println("${index + 1}. ${post.title}")
                        }
                        print(
                                "\nEnter the number of the blog to update or enter 0 to return to the main menu: "
                        )
                        val postNumber = readln().toInt()
                        if (postNumber in 1..posts.size) {
                            val postToUpdate = posts[postNumber - 1]
                            print("Enter the new title: ")
                            val newTitle = readln()
                            print("Enter the new content: ")
                            val newContent = readln()
                            blogService.updatePost(
                                    currentUserId,
                                    postToUpdate.id,
                                    newTitle,
                                    newContent
                            )
                            println("Blog updated.")
                            Thread.sleep(2000)
                        } else if (postNumber == 0) {
                            continue
                        } else {
                            println("Invalid Blog number.")
                        }
                    }
                    5 -> {
                        val posts = blogService.getPostsByUser(currentUserId)
                        if (posts.isEmpty()) {
                            println("\nYou have no blogs.")
                            print("\nEnter 0 to return to the main menu: ")
                            val option = readln().toInt()
                            if (option == 0) {
                                continue
                            }
                        }
                        println("\nYour blogs:\n")
                        posts.forEachIndexed { index, post ->
                            println("${index + 1}. ${post.title}")
                        }
                        print(
                                "\nEnter the number of the blog to delete or enter 0 to return to the main menu: "
                        )
                        val postNumber = readln().toInt()
                        if (postNumber in 1..posts.size) {
                            val postToDelete = posts[postNumber - 1]
                            blogService.deletePost(currentUserId, postToDelete.id)
                            println("Blog deleted.")
                            Thread.sleep(2000)
                        } else if (postNumber == 0) {
                            continue
                        } else {
                            println("Invalid Blog number.")
                        }
                    }
                    6 -> {
                        val posts = blogService.getPostsByUser(currentUserId)
                        if (posts.isEmpty()) {
                            println("\nYou have no blogs.")
                            print("\nEnter 0 to return to the main menu: ")
                            val option = readln().toInt()
                            if (option == 0) {
                                continue
                            }
                        }
                        println("\nYour blogs:\n")
                        posts.forEachIndexed { index, post ->
                            println(
                                    "${index + 1}. ${post.title} - ${if (post.published) "Published" else "Unpublished"}"
                            )
                        }

                        print("\nEnter 0 to return to the main menu: ")
                        val returnMenu = readln().toInt()
                        if (returnMenu == 0) {
                            continue
                        }
                    }
                    7 -> {
                        println("Goodbye $username")
                        break
                    }
                    else -> println("Invalid choice")
                }
            }
        } else {
            println("Invalid username or password")
        }
    } catch (e: Exception) {
        println("An error occurred: ${e.message}")
    }
}
