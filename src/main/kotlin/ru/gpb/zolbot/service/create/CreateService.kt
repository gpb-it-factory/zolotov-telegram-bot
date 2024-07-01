package ru.gpb.zolbot.service.create

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
class CreateService(private val webClient: WebClient) {

    private val logger = LoggerFactory.getLogger(CreateService::class.java)

    fun createAccount(user: User): FrontApiCreateResponse {
        logger.info("Sending user to middle layer")
        val response = webClient.post()
            .uri("/createaccount")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(user), User::class.java)
            .exchangeToMono { transformResponseByCodes(it) }
            .timeout(Duration.ofSeconds(5))

        return try {
            response.block() ?: FrontApiCreateResponse.Error()
        } catch (e: Exception) {
            FrontApiCreateResponse.Error()
        }
    }

    private fun transformResponseByCodes(item: ClientResponse): Mono<FrontApiCreateResponse> {

        return when {
            item.statusCode().isSameCodeAs(HttpStatus.NO_CONTENT) -> {
                Mono.just(FrontApiCreateResponse.Success())
            }

            item.statusCode().isSameCodeAs(HttpStatus.FORBIDDEN) -> {
                item.bodyToMono(FrontApiCreateResponse.Register::class.java)
            }

            item.statusCode().isSameCodeAs(HttpStatus.CONFLICT) -> {
                item.bodyToMono(FrontApiCreateResponse.Problem::class.java)
            }

            else -> {
                item.bodyToMono(FrontApiCreateResponse.Error::class.java)
            }
        }
    }
}
