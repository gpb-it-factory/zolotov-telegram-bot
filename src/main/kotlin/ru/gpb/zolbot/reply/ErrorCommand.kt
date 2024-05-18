package ru.gpb.zolbot.reply

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.gpb.zolbot.utils.Utils

class ErrorCommand : ReplyStrategy {

    override fun invoke(chatId: Long): SendMessage {
        return Utils.sendReply("Only text allowed!", chatId)
    }
}