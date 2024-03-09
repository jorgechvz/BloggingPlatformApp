package services
import com.google.gson.Gson
import java.io.File
import models.BlogPost
import java.util.UUID

public class BlogService {
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
    fun getPostsByUser(userId: String): List<BlogPost> {
        val gson = Gson()
        val json = File("posts.json").readText()
        val posts = gson.fromJson(json, Array<BlogPost>::class.java).toList()
        return posts.filter { it.userId == userId }
    }
    fun getPublishedPostsByUser(userId: String): List<BlogPost> {
        val gson = Gson()
        val json = File("posts.json").readText()
        val posts = gson.fromJson(json, Array<BlogPost>::class.java).toList()
        return posts.filter { it.userId == userId && it.published }
    }
    fun getUnpublishedPostsByUser(userId: String): List<BlogPost> {
        val gson = Gson()
        val json = File("posts.json").readText()
        val posts = gson.fromJson(json, Array<BlogPost>::class.java).toList()
        return posts.filter { it.userId == userId && !it.published }
    }
    fun findPost(userId: String,id: String): BlogPost? {
        val gson = Gson()
        val json = File("posts.json").readText()
        val blogPosts = gson.fromJson(json, Array<BlogPost>::class.java).toList()
        return blogPosts.find { it.id == id && it.userId == userId }
    }
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