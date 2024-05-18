package ru.gpb.zolbot.reply

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

interface ReplyStrategy {
    operator fun invoke(chatId: Long): SendMessage
}