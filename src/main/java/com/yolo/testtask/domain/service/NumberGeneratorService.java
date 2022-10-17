package com.yolo.testtask.domain.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class NumberGeneratorService {

    public int generateNumber(){
        return ThreadLocalRandom.current().nextInt(1, 100);
    }

}
