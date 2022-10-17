package com.yolo.testtask.rest;

import com.yolo.testtask.domain.service.GameService;
import com.yolo.testtask.domain.service.NumberGeneratorService;
import com.yolo.testtask.rest.config.ApiKeyAuthentication;
import com.yolo.testtask.rest.config.ApiKeyAuthenticationConverter;
import com.yolo.testtask.rest.config.ApiKeyAuthenticationManager;
import com.yolo.testtask.rest.config.SecurityConfiguration;
import com.yolo.testtask.rest.exception.RestControllerExceptionHandler;
import com.yolo.testtask.rest.resource.bet.BetResource;
import com.yolo.testtask.rest.resource.bet.BetResourceConverter;
import com.yolo.testtask.rest.resource.gameresult.GameResultResource;
import com.yolo.testtask.rest.resource.gameresult.GameResultResourceConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static com.yolo.testtask.rest.resource.bet.BetResourceBuilder.aBetResource;
import static com.yolo.testtask.rest.resource.gameresult.GameResultResourceBuilder.aGameResultResource;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = GameRestController.class)
@Import({RestControllerExceptionHandler.class,
        ApiKeyAuthentication.class,
        ApiKeyAuthenticationConverter.class,
        ApiKeyAuthenticationManager.class,
        SecurityConfiguration.class,
        GameService.class,
        BetResourceConverter.class,
        GameResultResourceConverter.class,
        String.class})
class GameRestControllerIntegrationTest {


    @Autowired
    private WebTestClient webClient;

    @MockBean
    private NumberGeneratorService numberGeneratorService;

    @Test
    void shouldPlayTheGame() {
        //given
        final BetResource betResource = aBetResource();
        final GameResultResource expectedResult =
                aGameResultResource().betId("TST123").win(80.19).build();
        given(numberGeneratorService.generateNumber()).willReturn(12);

        //then
        webClient.post()
                .uri("/playgame")
                .contentType(MediaType.APPLICATION_JSON)
                .header("test_token", "12344")
                .body(BodyInserters.fromValue(betResource))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResultResource.class).isEqualTo(expectedResult);
    }

    @Test
    void shouldValidateInput() {
        //given
        final BetResource betResource = aBetResource();
        betResource.setBetId(null);
        given(numberGeneratorService.generateNumber()).willReturn(12);

        //then
        webClient.post()
                .uri("/playgame")
                .contentType(MediaType.APPLICATION_JSON)
                .header("test_token", "12344")
                .body(BodyInserters.fromValue(betResource))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class)
                .isEqualTo("{\"type\":\"VALIDATION\",\"messages\":{\"NotBlank.betResource.betId\":[\"must not be blank\"]}}");
    }
    @Test

    void shouldReturn401OnNoHeader() {
        //given
        final BetResource betResource = aBetResource();
        given(numberGeneratorService.generateNumber()).willReturn(12);

        //then
        webClient.post()
                .uri("/playgame")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(betResource))
                .exchange()
                .expectStatus().is4xxClientError();
    }
}