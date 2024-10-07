package com.tifd.tugasm3.screen

data class GitHubUser(
    val login: String,
    val name: String?,
    val followers: Int,
    val following: Int,
    val avatar_url: String
)

