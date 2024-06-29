package ru.gpb.zolbot.reply

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.gpb.zolbot.models.User
import ru.gpb.zolbot.service.FrontApiResponse
import ru.gpb.zolbot.service.RegisterService
import ru.gpb.zolbot.utils.MessageUtils

@Component
class RegisterCommand(private val registerService: RegisterService) : ReplyStrategy {

    override fun invoke(update: Update): SendMessage {
        val operationCode = registerService.registerUser(User(update.message.from.id, update.message.from.userName))

        return when (operationCode) {
            is FrontApiResponse.Success -> {
                MessageUtils.createSendMessage(
                    "User created",
                    update.message.chatId
                )
            }

            is FrontApiResponse.Problem -> {
                MessageUtils.createSendMessage("User already registered", update.message.chatId)
            }

            is FrontApiResponse.Error -> {
                MessageUtils.createSendMessage("Unexpected error on server", update.message.chatId)
            }
        }
    }

    override fun command() = "/register"
}