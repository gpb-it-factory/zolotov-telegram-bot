package ru.gpb.zolbot

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.reactive.function.client.WebClient
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import reactor.core.publisher.Mono
import ru.gpb.zolbot.models.User
import ru.gpb.zolbot.reply.RegisterCommand
import ru.gpb.zolbot.reply.ReplyHandler
import ru.gpb.zolbot.service.FrontApiResponse
import ru.gpb.zolbot.service.RegisterService

@SpringBootTest
class ZolbotApplicationTests {

    @Autowired
    private lateinit var replyHandler: ReplyHandler

    @Test
    fun testStart() {
        val update = setTestMsg("/start")

        val result = replyHandler.handleUpdate(update).text

        assertEquals("Welcome in my GPB telegram bot", result)
    }

    @Test
    fun testPing() {
        val update = setTestMsg("/ping")

        val result = replyHandler.handleUpdate(update).text

        assertEquals("pong", result)
    }

    @Autowired
    private lateinit var registrationService: RegisterService

    @Test
    fun testRegister() {
        val testUser = org.telegram.telegrambots.meta.api.objects.User().apply {
            this.id = 21
            this.userName = "Pablo"
        }
        val update = setTestMsg("/register").apply {
            this.message.from = testUser
        }
        var testCount = 1

        val client = mockk<WebClient>()
        val service = RegisterService(client)
        val command = RegisterCommand(service)
        val replyHandler = ReplyHandler(listOf(command))

        every {
            client
                .post()
                .uri("/register")
                .contentType(any())
                .body(any(), User::class.java)
                .exchangeToMono<FrontApiResponse>(any())
                .timeout(any())
        } answers {
            when (testCount++) {
                1 -> Mono.just(FrontApiResponse.Success())
                2 -> Mono.just(FrontApiResponse.Problem())
                else -> Mono.just(FrontApiResponse.Error())
            }
        }


        assertEquals("User created", replyHandler.handleUpdate(update).text)
        assertEquals("User already registered", replyHandler.handleUpdate(update).text)
        assertEquals("Unexpected error on server", replyHandler.handleUpdate(update).text)
    }

    private fun setTestMsg(text: String): Update {
        val chat = Chat().apply { this.id = 1L }
        val msg = Message().apply {
            this.text = text
            this.chat = chat
        }
        return Update().apply { this.message = msg }
    }
}