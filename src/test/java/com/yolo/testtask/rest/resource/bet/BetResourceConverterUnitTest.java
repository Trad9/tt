package com.yolo.testtask.rest.resource.bet;

import com.yolo.testtask.domain.domain.bet.Bet;
import org.junit.jupiter.api.Test;

import static com.yolo.testtask.rest.resource.bet.BetResourceBuilder.aBetResource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class BetResourceConverterUnitTest {

    private final BetResourceConverter converter = new BetResourceConverter();

    @Test
    void shouldConvertBetId(){
        //given
        final BetResource betResource = aBetResource();
        betResource.betId = "TST123";

        //when
        final Bet result = converter.toDomain(betResource);

        //then
        assertThat(result.getBetId()).isEqualTo("TST123");
    }

    @Test
    void shouldConvertBetAmount(){
        //given
        final BetResource betResource = aBetResource();
        betResource.betAmount = 99.53;

        //when
        final Bet result = converter.toDomain(betResource);

        //then
        assertThat(result.getBetAmount()).isEqualTo(99.53);
    }

    @Test
    void shouldConvertNumber(){
        //given
        final BetResource betResource = aBetResource();
        betResource.number = 14;

        //when
        final Bet result = converter.toDomain(betResource);

        //then
        assertThat(result.getNumber()).isEqualTo(14);
    }

}