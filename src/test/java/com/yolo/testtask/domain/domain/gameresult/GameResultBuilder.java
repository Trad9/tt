package com.yolo.testtask.domain.domain.gameresult;

public class GameResultBuilder extends GameResult.GameResultBuilder{

    public static GameResult.GameResultBuilder aGameResult(){
        return new GameResultBuilder()
                .betId("1234")
                .win(80.19);
    }

}