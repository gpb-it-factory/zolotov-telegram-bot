package ru.gpb.zolbot.reply

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.gpb.zolbot.utils.MessageUtils

@Component
class ReplyHandler(list: List<ReplyStrategy>) {
    private val logger = LoggerFactory.getLogger(ReplyHandler::class.java)

    private var map: Map<String, ReplyStrategy> = list.associateBy { it.command().text }

    fun handleUpdate(update: Update): SendMessage {
        return if (update.message.hasText()) {
            val msgText = update.message.text
            logger.info("User enter \"$msgText\"")
            map[msgText]?.invoke(update) ?: MessageUtils.createSendMessage(
                "I don't know this command. Yet...",
                update.message.chatId
            )
        } else {
            MessageUtils.createSendMessage("Only text allowed!", update.message.chatId)
        }
            .also { logger.info("Reply is sent to user") }

    }
}