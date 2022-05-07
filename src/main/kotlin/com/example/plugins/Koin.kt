package com.example.plugins

import io.ktor.application.Application
import io.ktor.application.install
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(){
    install(Koin){
        slf4jLogger()
    }
}