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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.yolo.testtask.rest.resource.bet.BetResourceBuilder.aBetResource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = GameRestController.class)
@Import({RestControllerExceptionHandler.class,
        ApiKeyAuthentication.class,
        ApiKeyAuthenticationConverter.class,
        ApiKeyAuthenticationManager.class,
        SecurityConfiguration.class,
        GameService.class,
        NumberGeneratorService.class,
        BetResourceConverter.class,
        GameResultResourceConverter.class,
        String.class})
public class RtpIntegrationTest {

        @Autowired
        private WebTestClient webClient;

        final BetResource betResource = aBetResource();

        private static final AtomicInteger victoryCount  = new AtomicInteger(0);

        @Test
        void rtpTest(){
            BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(1000000);

            for( int i = 0;i< 1000000;i++)
            {
                blockingQueue.offer(new FutureTask<>(new GameRun(webClient,betResource,victoryCount)));
            }

            ThreadPoolExecutor executor =
                    new ThreadPoolExecutor(24, 24,0L, TimeUnit.MILLISECONDS,
                            blockingQueue);
            executor.prestartAllCoreThreads();

            try {
                executor.awaitTermination(120, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }

            double ratio = ((double)(victoryCount.get())/1000000)*100;

            System.out.println("-------------------Overall player wins : " + victoryCount.get() );
            System.out.println("-------------------STP ratio is : " + ratio + " %");

            assertThat(victoryCount.get() >0).isTrue();

        }

    class GameRun implements Callable<GameResultResource> {

        private final WebTestClient webClient;
        private final BetResource betResource;
        private final AtomicInteger victoryCounter;

        public GameRun(WebTestClient webClient, BetResource betResource, AtomicInteger victoryCounter){
            this.webClient=webClient;
            this.betResource=betResource;
            this.victoryCounter = victoryCounter;
        }

        @Override
        public GameResultResource call() {
            GameResultResource response = webClient.post()
                    .uri("/playgame")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("test_token", "12344")
                    .body(BodyInserters.fromValue(betResource))
                    .exchange().returnResult(GameResultResource.class).getResponseBody().blockFirst();
            if (response.getWin()>0.0) {victoryCounter.getAndIncrement();}

            return response;
        }

    }
}



