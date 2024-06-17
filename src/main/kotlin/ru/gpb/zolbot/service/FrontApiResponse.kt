package ru.gpb.zolbot.service

import kotlinx.serialization.Serializable

@Serializable
data class Response(val message: String = "")


sealed class FrontApiResponse {
    data class Success(val message: String = "User registered") : FrontApiResponse()
    data class Problem(val message: String = "User already registered") : FrontApiResponse()
    data class Error(val message: String = "Something went wrong") : FrontApiResponse()
}