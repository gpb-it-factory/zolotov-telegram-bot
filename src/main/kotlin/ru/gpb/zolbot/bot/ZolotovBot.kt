package ru.gpb.zolbot.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import ru.gpb.zolbot.reply.ReplyHandler

@Component
class ZolotovBot(
    @Value("\${telegram.token}")
    private val token: String,
    private val replyHandler: ReplyHandler
) : TelegramLongPollingBot(token) {
    @Value("\${telegram.botName}")
    private val botName: String = ""

    override fun getBotUsername() = botName

    override fun onUpdateReceived(update: Update) {
        execute(replyHandler.handleUpdate(update))
    }
}