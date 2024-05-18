package ru.gpb.zolbot.utils

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class Utils {
    companion object {
        fun sendReply(text: String, chatId: Long): SendMessage {
            return SendMessage().apply {
                this.chatId = chatId.toString()
                this.text = text
            }
        }
    }
}