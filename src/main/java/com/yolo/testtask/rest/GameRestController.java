package com.yolo.testtask.rest;

import com.yolo.testtask.domain.domain.bet.Bet;
import com.yolo.testtask.domain.port.input.GameServiceUseCase;
import com.yolo.testtask.rest.resource.bet.BetResource;
import com.yolo.testtask.rest.resource.bet.BetResourceConverter;
import com.yolo.testtask.rest.resource.gameresult.GameResultResource;
import com.yolo.testtask.rest.resource.gameresult.GameResultResourceConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GameRestController {

    private final GameServiceUseCase gameService;
    private final BetResourceConverter betResourceConverter;
    private final GameResultResourceConverter winResourceConverter;

    @PostMapping(value = "/playgame")
    public Mono<GameResultResource> playGame( @NotNull @Valid @RequestBody final BetResource betResource){
        log.info("=====Received : " + betResource.toString() );
        final Bet bet = betResourceConverter.toDomain(betResource);
        return winResourceConverter.toResource(gameService.getGameResults(bet));
    }


}
