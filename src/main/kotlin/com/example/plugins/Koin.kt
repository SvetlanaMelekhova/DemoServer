package com.example.plugins

import com.example.di.koinModule
import io.ktor.application.Application
import io.ktor.application.install
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(){
    install(Koin){
        modules(koinModule)
        slf4jLogger()

    }
}