package com.yolo.testtask.domain.service;

import com.yolo.testtask.domain.domain.bet.Bet;
import com.yolo.testtask.domain.domain.gameresult.GameResult;
import com.yolo.testtask.domain.port.input.GameServiceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService implements GameServiceUseCase {

    private final NumberGeneratorService numberGeneratorService;
    private final static ThreadPoolExecutor gameExecutor =
            new ThreadPoolExecutor(3, 24, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000000));
    @Override
    public Mono<GameResult> getGameResults(final Bet bet) {
        CompletableFuture<GameResult> gameRunFuture = CompletableFuture.supplyAsync(() -> {
                final int generatedNumber = numberGeneratorService.generateNumber();
                log.info("=====Generated number = {} for betId: {}", generatedNumber, bet.getBetId());
                final int playerNumber = bet.getNumber();
                double winAmount;

                winAmount = generatedNumber < playerNumber ?
                        bet.getBetAmount() * (99 / (double) (100 - playerNumber)) : 0.00;


                log.info("=====Calculated sum of win = {} for betId: {}", winAmount, bet.getBetId());
                return
                        GameResult.builder()
                                .betId(bet.getBetId())
                                .win(winAmount)
                                .build();
            }, gameExecutor);

            return Mono.fromFuture(gameRunFuture);
        }

    }

