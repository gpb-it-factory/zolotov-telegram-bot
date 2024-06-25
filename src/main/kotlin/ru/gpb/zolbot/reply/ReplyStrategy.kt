package ru.gpb.zolbot.reply

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

interface ReplyStrategy {
    operator fun invoke(update: Update): SendMessage

    fun command(): String
}