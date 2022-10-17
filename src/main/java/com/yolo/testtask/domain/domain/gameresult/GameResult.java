package com.yolo.testtask.domain.domain.gameresult;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GameResult {
    String betId;
    double win;

}
