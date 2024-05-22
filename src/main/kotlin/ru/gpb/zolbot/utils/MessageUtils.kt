package ru.gpb.zolbot.utils

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class MessageUtils {
    companion object {
        fun createSendMessage(text: String, chatId: Long): SendMessage {
            return SendMessage().apply {
                this.text = text
                this.chatId = chatId.toString()
            }
        }
    }
}