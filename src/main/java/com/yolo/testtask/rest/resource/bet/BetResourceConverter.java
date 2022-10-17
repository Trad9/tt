package com.yolo.testtask.rest.resource.bet;

import com.yolo.testtask.domain.domain.bet.Bet;
import org.springframework.stereotype.Component;

@Component
public class BetResourceConverter {
    public Bet toDomain(final BetResource betResource) {
        return Bet.builder()
                .betId(betResource.betId)
                .number(betResource.number)
                .betAmount(betResource.betAmount)
                .build();
    }
}
