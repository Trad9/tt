package com.yolo.testtask.rest.resource.bet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BetResource {
    @NotBlank
    String betId;

    @Min(1)
    int number;

    @Min(1)
    @Max(100)
    double betAmount;
}
