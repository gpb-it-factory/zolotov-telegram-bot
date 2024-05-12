package com.example.zolbot

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class ZolotovBot(
    @Value("\${telegram.token}")
    token: String
) : TelegramLongPollingBot(token) {
    @Value("\${telegram.botName}")
    private val botName: String = ""

    override fun getBotUsername() = botName

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            replyLogic(update)
        }
    }

    private fun replyLogic(update: Update) {
        val chatId = update.message.chatId
        if (update.message.hasText()) {
            when (update.message.text) {
                "/start" -> sendReply("Welcome in my GPB telegram bot", chatId)
                "/ping" -> sendReply("pong", chatId)
                else -> sendReply("I don't know this command. Yet...", chatId)
            }
        } else {
            sendReply("Only text allowed!", chatId)
        }
    }

    private fun sendReply(text: String, chatId: Long) {
        execute(
            SendMessage().apply {
                this.chatId = chatId.toString()
                this.text = text
            }
        )
    }
}