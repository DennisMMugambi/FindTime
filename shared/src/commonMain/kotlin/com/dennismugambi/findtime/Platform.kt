package com.dennismugambi.findtime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform