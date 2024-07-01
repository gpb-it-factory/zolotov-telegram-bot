package ru.gpb.zolbot.service.register

sealed class FrontApiReisterResponse {
    data class Success(val message: String = "User registered") : FrontApiReisterResponse()
    data class Problem(val message: String = "User already registered") : FrontApiReisterResponse()
    data class Error(val message: String = "Something went wrong") : FrontApiReisterResponse()
}