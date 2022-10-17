package com.yolo.testtask.domain.service;

import com.yolo.testtask.domain.domain.bet.Bet;
import com.yolo.testtask.domain.domain.gameresult.GameResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.yolo.testtask.domain.domain.bet.BetBuilder.aBet;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameServiceUnitTest {
    @Mock
    private NumberGeneratorService numberGeneratorService;

    @InjectMocks
    private GameService gameService;

    @Test
    void shouldReturnBetId(){
        //given
        given(numberGeneratorService.generateNumber()).willReturn(0);
        final Bet bet = aBet().betId("TST123").build();

        //when
        GameResult result = gameService.getGameResults(bet).block();

        //then
        assertThat(result).isNotNull();
        assertThat(result.getBetId()).isEqualTo("TST123");
    }

    @Test
    void shouldReturnZeroWinOnPlayersNumLowerThanGenerated(){
        //given
        given(numberGeneratorService.generateNumber()).willReturn(45);
        final Bet bet = aBet().number(35).build();

        //when
        GameResult result = gameService.getGameResults(bet).block();

        //then
        assertThat(result).isNotNull();
        assertThat(result.getWin()).isEqualTo(0.0);
    }

    @Test
    void shouldReturnHigherWinOnPlayersNumHigherThanGenerated(){
        //given
        given(numberGeneratorService.generateNumber()).willReturn(45);
        final Bet bet = aBet().number(50).betAmount(40.5).build();

        //when
        GameResult result = gameService.getGameResults(bet).block();

        //then
        assertThat(result).isNotNull();
        assertThat(result.getWin()).isGreaterThan(40.5);
        assertThat(result.getWin()).isEqualTo(80.19);
    }


}