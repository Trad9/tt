package com.yolo.testtask.rest.resource.gameresult;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class GameResultResource {
    String betId;
    double win;

}
