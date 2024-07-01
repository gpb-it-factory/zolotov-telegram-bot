package ru.gpb.zolbot.service.register

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

    fun registerUser(user: User): FrontApiReisterResponse {
        logger.info("Sending user to middle layer")
        val response = webClient.post()
            .uri("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(user), User::class.java)
            .exchangeToMono { transformResponseByCodes(it) }
            .timeout(Duration.ofSeconds(5))

        return try {
            response.block() ?: FrontApiReisterResponse.Error()
        } catch (e: Exception) {
            FrontApiReisterResponse.Error()
        }
    }

    private fun transformResponseByCodes(item: ClientResponse): Mono<FrontApiReisterResponse> {

        return when {
            item.statusCode().isSameCodeAs(HttpStatus.NO_CONTENT) -> {
                Mono.just(FrontApiReisterResponse.Success())
            }

            item.statusCode().isSameCodeAs(HttpStatus.CONFLICT) -> {
                item.bodyToMono(FrontApiReisterResponse.Problem::class.java)
            }

            else -> {
                item.bodyToMono(FrontApiReisterResponse.Error::class.java)
            }
        }
    }
}
