package ru.gpb.zolbot

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.telegram.telegrambots.meta.api.objects.Update
import ru.gpb.zolbot.reply.ReplyHandler

@SpringBootTest
class ZolbotApplicationTests {

	@Autowired
	private lateinit var replyHandler: ReplyHandler

	@Test
	fun testStart() {
		val update = mockk<Update>()

		every { update.hasMessage() } returns true
		every { update.message.hasText() } returns true
		every { update.message.text } returns "/start"
		every { update.message.chatId } returns 1L

		assertEquals("Welcome in my GPB telegram bot", replyHandler.handleUpdate(update).text)
	}

	@Test
	fun testPing() {
		val update = mockk<Update>()

		every { update.hasMessage() } returns true
		every { update.message.hasText() } returns true
		every { update.message.text } returns "/ping"
		every { update.message.chatId } returns 1L

		assertEquals("pong", replyHandler.handleUpdate(update).text)
	}

}
