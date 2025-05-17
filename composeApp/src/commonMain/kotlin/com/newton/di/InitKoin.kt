package com.newton.di

import org.koin.core.context.*
import org.koin.dsl.*

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }
}