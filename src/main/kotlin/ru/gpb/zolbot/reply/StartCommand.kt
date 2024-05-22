package ru.gpb.zolbot.reply

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.gpb.zolbot.utils.Commands
import ru.gpb.zolbot.utils.MessageUtils

@Component
class StartCommand : ReplyStrategy {

    override fun invoke(update: Update): SendMessage {
        return MessageUtils.createSendMessage("Welcome in my GPB telegram bot", update.message.chatId)
    }

    override fun command(): Commands = Commands.START
}