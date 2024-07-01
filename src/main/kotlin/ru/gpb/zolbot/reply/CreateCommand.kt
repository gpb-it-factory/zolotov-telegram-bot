package ru.gpb.zolbot.reply

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.gpb.zolbot.models.User
import ru.gpb.zolbot.service.create.CreateService
import ru.gpb.zolbot.service.create.FrontApiCreateResponse
import ru.gpb.zolbot.service.register.FrontApiReisterResponse
import ru.gpb.zolbot.service.register.RegisterService
import ru.gpb.zolbot.utils.MessageUtils

@Component
class CreateCommand(private val createService: CreateService) : ReplyStrategy {

    override fun invoke(update: Update): SendMessage {
        val operationCode = createService.createAccount(User(update.message.from.id, update.message.from.userName))

        return when (operationCode) {
            is FrontApiCreateResponse.Success -> {
                MessageUtils.createSendMessage(
                    "Account created",
                    update.message.chatId
                )
            }

            is FrontApiCreateResponse.Register -> {
                MessageUtils.createSendMessage(
                    "First you need to register",
                    update.message.chatId
                )
            }

            is FrontApiCreateResponse.Problem -> {
                MessageUtils.createSendMessage("Account already been created", update.message.chatId)
            }

            is FrontApiCreateResponse.Error -> {
                MessageUtils.createSendMessage("Unexpected error on server", update.message.chatId)
            }
        }
    }

    override fun command() = "/createaccount"
}