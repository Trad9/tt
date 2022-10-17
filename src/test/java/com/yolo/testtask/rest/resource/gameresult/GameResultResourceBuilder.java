package com.yolo.testtask.rest.resource.gameresult;

public class GameResultResourceBuilder extends GameResultResource.GameResultResourceBuilder{

    public static GameResultResource.GameResultResourceBuilder aGameResultResource(){
        return new GameResultResourceBuilder()
                .betId("1234")
                .win(80.19);
    }
}
