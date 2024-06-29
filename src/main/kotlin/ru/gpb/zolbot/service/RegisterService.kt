package ru.gpb.zolbot.service

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.gpb.zolbot.models.User
import java.time.Duration

@Service
class RegisterService(private val webClient: WebClient) {

    private val logger = LoggerFactory.getLogger(RegisterService::class.java)

    fun registerUser(user: User): FrontApiResponse {
        logger.info("Sending user to middle layer")
        val response = webClient.post()
            .uri("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(user), User::class.java)
            .exchangeToMono { transformResponseByCodes(it) }
            .timeout(Duration.ofSeconds(5))

        return try {
            response.block() ?: FrontApiResponse.Error()
        } catch (e: Exception) {
            FrontApiResponse.Error()
        }
    }

    private fun transformResponseByCodes(item: ClientResponse): Mono<FrontApiResponse> {

        return when {
            item.statusCode().isSameCodeAs(HttpStatus.NO_CONTENT) -> {
                Mono.just(FrontApiResponse.Success())
            }

            item.statusCode().isSameCodeAs(HttpStatus.CONFLICT) -> {
                item.bodyToMono(FrontApiResponse.Problem::class.java)
            }

            else -> {
                item.bodyToMono(FrontApiResponse.Error::class.java)
            }
        }
    }
}
