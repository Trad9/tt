package com.yolo.testtask.domain.port.input;

import com.yolo.testtask.domain.domain.bet.Bet;
import com.yolo.testtask.domain.domain.gameresult.GameResult;
import reactor.core.publisher.Mono;

public interface GameServiceUseCase {
    Mono<GameResult> getGameResults(final Bet bet);

}
