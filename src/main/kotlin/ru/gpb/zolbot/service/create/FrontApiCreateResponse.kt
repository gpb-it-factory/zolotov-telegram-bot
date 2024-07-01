package ru.gpb.zolbot.service.create

sealed class FrontApiCreateResponse {
    data class Success(val message: String = "Account created") : FrontApiCreateResponse()
    data class Register(val message: String = "First you need to register") : FrontApiCreateResponse()
    data class Problem(val message: String = "Account already been created") : FrontApiCreateResponse()
    data class Error(val message: String = "Something went wrong on server") : FrontApiCreateResponse()
}