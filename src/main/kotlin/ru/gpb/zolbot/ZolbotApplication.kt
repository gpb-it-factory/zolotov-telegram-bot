package ru.gpb.zolbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ZolbotApplication

fun main(args: Array<String>) {
    runApplication<ZolbotApplication>(*args)
}
