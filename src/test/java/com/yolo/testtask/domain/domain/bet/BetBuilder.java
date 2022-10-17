package com.yolo.testtask.domain.domain.bet;

public class BetBuilder extends Bet.BetBuilder {

    public static Bet.BetBuilder aBet() {
        return new BetBuilder().betId("1234").betAmount(40.5).number(50);
    }

}