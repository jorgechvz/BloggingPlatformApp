package services
import com.google.gson.Gson
import java.io.File
import models.BlogPost
import java.util.UUID

public class BlogService {
    // Add a createPost method to the BlogService class to create a new post
    fun createPost(userId: String,title: String, content: String): BlogPost {
        val postIds = UUID.randomUUID().toString()
        val post = BlogPost(userId,postIds, title, content)
        val gson = Gson()
        val json = File("posts.json").readText()
        val posts = gson.fromJson(json, Array<BlogPost>::class.java).toMutableList()
        posts.add(post)
        val updatedJson = gson.toJson(posts)
        File("posts.json").writeText(updatedJson)
        return post
    }
    // Add a getPosts method to the BlogService class to get all posts by user ID
    fun getPostsByUser(userId: String): List<BlogPost> {
        val gson = Gson()
        val json = File("posts.json").readText()
        val posts = gson.fromJson(json, Array<BlogPost>::class.java).toList()
        return posts.filter { it.userId == userId }
    }
    // Add a getPublishedPostsByUser method to the BlogService class to get all published posts
    fun getPublishedPostsByUser(userId: String): List<BlogPost> {
        val gson = Gson()
        val json = File("posts.json").readText()
        val posts = gson.fromJson(json, Array<BlogPost>::class.java).toList()
        return posts.filter { it.userId == userId && it.published }
    }
    // Add a getUnpublishedPostsByUser method to the BlogService class to get all unpublished posts
    fun getUnpublishedPostsByUser(userId: String): List<BlogPost> {
        val gson = Gson()
        val json = File("posts.json").readText()
        val posts = gson.fromJson(json, Array<BlogPost>::class.java).toList()
        return posts.filter { it.userId == userId && !it.published }
    }
    // Add a findPost method to the BlogService class to find a post by user ID and post ID
    fun findPost(userId: String,id: String): BlogPost? {
        val gson = Gson()
        val json = File("posts.json").readText()
        val blogPosts = gson.fromJson(json, Array<BlogPost>::class.java).toList()
        return blogPosts.find { it.id == id && it.userId == userId }
    }
    // Add a deletePost method to the BlogService class to delete a post by user ID and post ID
    fun deletePost(userId: String, id: String): Boolean {
        val post = findPost(userId, id)
        val gson = Gson()
        val json = File("posts.json").readText()
        val blogPosts = gson.fromJson(json, Array<BlogPost>::class.java).toMutableList()
        val result = blogPosts.remove(post)
        if (result) {
            val updatedJson = gson.toJson(blogPosts)
            File("posts.json").writeText(updatedJson)
        }
        return result
    }
    // Add an updatePost method to the BlogService class to update a post by user ID and post ID
    fun updatePost(userId: String,id: String, title: String, content: String): BlogPost? {
        val post = findPost(userId,id)
        if (post != null) {
            val updatedPost = post.copy(title = title, content = content)
            val gson = Gson()
            val json = File("posts.json").readText()
            val blogPosts = gson.fromJson(json, Array<BlogPost>::class.java).toMutableList()
            blogPosts.remove(post)
            blogPosts.add(updatedPost)
            val updatedJson = gson.toJson(blogPosts)
            File("posts.json").writeText(updatedJson)
            return updatedPost
        }
        return null
    }
    // Add a publishPost method to the BlogService class to publish a post by user ID and post ID
    fun publishPost(userId: String,id: String): BlogPost? {
        val post = findPost(userId,id)
        if (post != null) {
            val updatedPost = post.copy(published = true)
            val gson = Gson()
            val json = File("posts.json").readText()
            val blogPosts = gson.fromJson(json, Array<BlogPost>::class.java).toMutableList()
            blogPosts.remove(post)
            blogPosts.add(updatedPost)
            val updatedJson = gson.toJson(blogPosts)
            File("posts.json").writeText(updatedJson)
            return updatedPost
        }
        return null
    }
    // Add an unpublishPost method to the BlogService class to unpublish a post by user ID and post ID
    fun unpublishPost(userId: String,id: String): BlogPost? {
        val post = findPost(userId,id)
        if (post != null) {
            val updatedPost = post.copy(published = false)
            val gson = Gson()
            val json = File("posts.json").readText()
            val blogPosts = gson.fromJson(json, Array<BlogPost>::class.java).toMutableList()
            blogPosts.remove(post)
            blogPosts.add(updatedPost)
            val updatedJson = gson.toJson(blogPosts)
            File("posts.json").writeText(updatedJson)
            return updatedPost
        }
        return null
    }
}