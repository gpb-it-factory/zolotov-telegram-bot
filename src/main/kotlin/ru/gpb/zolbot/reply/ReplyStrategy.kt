package ru.gpb.zolbot.reply

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.gpb.zolbot.utils.Commands

interface ReplyStrategy {
    operator fun invoke(update: Update): SendMessage

    fun command(): Commands
}