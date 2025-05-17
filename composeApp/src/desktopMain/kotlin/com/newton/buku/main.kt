package com.newton.buku

import androidx.compose.ui.window.*
import com.newton.di.*

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "online book store",
        ) {
            App(
            )
        }
    }
}