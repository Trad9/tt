package com.yolo.testtask.rest.resource.bet;


public class BetResourceBuilder {

    public static BetResource aBetResource(){
        BetResource resource = new BetResource();
        resource.betId = "TST123";
        resource.betAmount = 40.5;
        resource.number = 50;
        return resource;
    }

}
