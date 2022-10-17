package com.yolo.testtask.domain.service;

import com.yolo.testtask.domain.domain.bet.Bet;
import com.yolo.testtask.domain.domain.gameresult.GameResult;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.yolo.testtask.domain.domain.bet.BetBuilder.aBet;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class RtpServiceTest {

    private final NumberGeneratorService numberGeneratorService = new NumberGeneratorService();
    private final GameService gameService = new GameService(numberGeneratorService);
    final Bet bet = aBet().build();
    private static final AtomicInteger victoryCount  = new AtomicInteger(0);


    @Test
    void rtpTest(){


        BlockingQueue<Runnable> blockingQueue =  new LinkedBlockingQueue<>(1000000);

        for( int i = 0;i< 1000000;i++)
        {
            blockingQueue.offer(new FutureTask<>(new ServiceGameRun(bet,victoryCount)));
        }

        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(24, 24,0, TimeUnit.SECONDS,
                        blockingQueue);
        executor.prestartAllCoreThreads();


        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        double ratio = ((double)(victoryCount.get())/1000000)*100;

        System.out.println("-------------------Overall player wins : " + victoryCount.get() );
        System.out.println("-------------------STP ratio is : " + ratio + " %");

        assertThat(victoryCount.get() >0).isTrue();

    }

    class ServiceGameRun implements Callable<GameResult> {
        private final Bet bet;
        private final AtomicInteger victoryCounter;

        public ServiceGameRun(Bet bet, AtomicInteger victoryCounter){
            this.bet =bet;
            this.victoryCounter = victoryCounter;
        }

        @Override
        public GameResult call() {
            GameResult response = gameService.getGameResults(bet).block();
            if (response.getWin() > 0.0) {victoryCounter.getAndIncrement();}
            return response;
        }
    }
}



