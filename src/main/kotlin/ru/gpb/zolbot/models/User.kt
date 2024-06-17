package ru.gpb.zolbot.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val userId: Long, val userName: String)