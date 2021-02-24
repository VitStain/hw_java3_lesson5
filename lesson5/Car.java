package lesson5;

import java.util.concurrent.BrokenBarrierException;

import java.util.concurrent.CyclicBarrier;


public class Car implements Runnable {
        private static int CARS_COUNT;
        private static boolean winner;


        static {
            CARS_COUNT = 0;
        }
        private Race race;
        private int speed;
        private String name;

    private CyclicBarrier cyclicBarrier;


        public String getName() {
            return name;
        }
        public int getSpeed() {
            return speed;
        }


        public Car(Race race, int speed, CyclicBarrier cyclicBarrier) {
            this.race = race;
            this.speed = speed;
            CARS_COUNT++;
            this.name = "Участник #" + CARS_COUNT;
            this.cyclicBarrier = cyclicBarrier;

        }
        @Override
        public void run() {
            try {
                System.out.println(this.name + " готовится");
                Thread.sleep(500 + (int)(Math.random() * 800));
                System.out.println(this.name + " готов");
                cyclicBarrier.await();
                cyclicBarrier.await();

            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            checkWin(this);

            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }


        private static synchronized void checkWin(Car c) {
            if (!winner) {
                System.out.println(c.name + " - WIN");
                winner = true;
            }
        }
    }

