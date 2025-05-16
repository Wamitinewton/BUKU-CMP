package com.newton.buku

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform