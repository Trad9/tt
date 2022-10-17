package com.yolo.testtask.domain.domain.bet;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Bet {
    String betId;
    int number;
    Double betAmount;
}
