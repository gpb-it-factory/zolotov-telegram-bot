package ru.gpb.zolbot.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.web.client.RestTemplate
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import ru.gpb.zolbot.bot.ZolotovBot
import ru.gpb.zolbot.service.CustomResponseErrorHandler
import ru.gpb.zolbot.utils.Constants

@Configuration
class Configuration {
    @Bean
    fun telegramBotApi(bot: ZolotovBot): TelegramBotsApi =
        TelegramBotsApi(DefaultBotSession::class.java).apply {
            registerBot(bot)
        }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .rootUri(Constants.MIDDLE)
            .defaultHeader("Content-Type", "application/json")
            .errorHandler(CustomResponseErrorHandler())
            .messageConverters(KotlinSerializationJsonHttpMessageConverter())
            .build()
    }
}