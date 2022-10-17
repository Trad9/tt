package com.yolo.testtask.rest.resource.gameresult;

import com.yolo.testtask.domain.domain.gameresult.GameResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GameResultResourceConverter {
    public Mono<GameResultResource> toResource(final Mono<GameResult> gameResult) {
        return gameResult.flatMap(roll ->{
            final GameResultResource resource = GameResultResource
                    .builder()
                    .betId(roll.getBetId())
                    .win(roll.getWin())
                    .build();
            log.info("=====Generated result " + resource.toString());
            return Mono.just(resource);
        });
    }
}
