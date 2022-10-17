package com.yolo.testtask.rest.resource.gameresult;

import com.yolo.testtask.domain.domain.gameresult.GameResult;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static com.yolo.testtask.domain.domain.gameresult.GameResultBuilder.aGameResult;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GameResultResourceConverterUnitTest {

    private final GameResultResourceConverter converter = new GameResultResourceConverter();

    @Test
    void shouldConvertBetId(){
        //given
        final Mono<GameResult> gameResult = Mono.just(aGameResult().betId("TST123").build());

        //when
        final GameResultResource result = converter.toResource(gameResult).block();

        //then
        assertThat(result).isNotNull();
        assertThat(result.getBetId()).isEqualTo("TST123");
    }

    @Test
    void shouldConvertWin(){
        //given
        final Mono<GameResult> gameResult = Mono.just(aGameResult().win(80.19).build());

        //when
        final GameResultResource result = converter.toResource(gameResult).block();

        //then
        assertThat(result).isNotNull();
        assertThat(result.getWin()).isEqualTo(80.19);
    }


}