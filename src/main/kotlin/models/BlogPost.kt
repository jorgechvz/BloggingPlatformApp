package models

data class BlogPost(val userId: String,val id: String ,val title: String, val content: String, val published: Boolean = false)