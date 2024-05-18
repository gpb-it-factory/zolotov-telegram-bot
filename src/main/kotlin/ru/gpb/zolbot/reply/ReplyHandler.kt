package ru.gpb.zolbot.reply

import org.slf4j.LoggerFactory
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class ReplyHandler(private var replyStrategy: ReplyStrategy = DefaultCommand()) {

    private val logger = LoggerFactory.getLogger(ReplyHandler::class.java)

    fun handleUpdate(update: Update): SendMessage {
        processUpdate(update)
        val chatId = update.message.chatId
        return replyStrategy(chatId).also { logger.info("Reply is sent to user") }
    }

    private fun setStrategy(replyStrategy: ReplyStrategy) {
        this.replyStrategy = replyStrategy
        logger.info("Reply strategy changed to ${replyStrategy.javaClass.simpleName}")
    }

    private fun processUpdate(update: Update) {
        if (update.message.hasText()) {
            logger.info("User enter \"${update.message.text}\"")
            when (update.message.text) {
                "/start" -> setStrategy(StartCommand())
                "/ping" -> setStrategy(PingCommand())
                else -> setStrategy(DefaultCommand())
            }
        } else {
            setStrategy(ErrorCommand())
        }
    }
}