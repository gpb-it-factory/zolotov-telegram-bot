package ru.gpb.zolbot.service

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.gpb.zolbot.models.User

@Service
class RegisterService(private val restTemplate: RestTemplate) {

    private val logger = LoggerFactory.getLogger(RegisterService::class.java)

    fun registerUser(user: User): FrontApiResponse {
        logger.info("Sending user to middle layer")
        val response = restTemplate.postForEntity("/register", user, Response::class.java)
        return transform(response)
    }

    private fun transform(item: ResponseEntity<Response>): FrontApiResponse {
        return when {
            item.statusCode.is2xxSuccessful -> FrontApiResponse.Success()

            item.statusCode.is4xxClientError -> {
                FrontApiResponse.Problem()
            }

            else -> FrontApiResponse.Error()
        }
    }
}
