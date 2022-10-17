package com.yolo.testtask.domain.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberGeneratorServiceUnitTest {

    NumberGeneratorService service = new NumberGeneratorService();

    @Test
    void shouldGenerateNumber() {
        //when
        int result = service.generateNumber();

        //then
        assertThat(result>0).isTrue();
    }
}