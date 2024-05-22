package ru.gpb.zolbot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import ru.gpb.zolbot.bot.ZolotovBot

@Configuration
class Configuration {
    @Bean
    fun telegramBotApi(bot: ZolotovBot): TelegramBotsApi =
        TelegramBotsApi(DefaultBotSession::class.java).apply {
            registerBot(bot)
        }
}