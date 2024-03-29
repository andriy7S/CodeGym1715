package com.codegym.task.task17.task1715;

import java.util.ArrayList;
import java.util.List;

/* 
Pharmacy
Implement the Runnable interface in the Pharmacy and Person classes.
All threads should run until isStopped is true.
Here's the logic for the Pharmacy class: drugController should make a random drug purchase (getRandomDrug)
in a random amount (getRandomCount) and wait 300 ms.
Here's the logic for the Person class: drugController should make a random drug sale (getRandomDrug)
in a random amount (getRandomCount) and wait 100 ms.
Put the synchronized keyword where necessary.


Requirements:
1. The Solution class must have a public static DrugController field called drugController.
2. The Solution class must have a public static boolean field called isStopped.
3. The Solution class must have a private static void waitAMoment() method that waits 100 ms.
4. The Pharmacy class must implement the Runnable interface.
5. The Pharmacy thread should run as long as isStopped is false.
6. The Pharmacy thread must use drugController to purchase a random drug (getRandomDrug) in a random amount (getRandomCount).
7. The Pharmacy thread should use the waitAMoment() method to wait 300 ms between purchases.
8. The Person class must implement the Runnable interface.
9. The Person thread should run as long as isStopped is false.
10. The Person thread must use drugController to sell a random drug (getRandomDrug) in a random amount (getRandomCount).
11. The Person thread should use the waitAMoment() method to wait 100 ms between purchases.
12. The methods of the DrugController class must be synchronized.

*/

public class Solution {
    public static DrugController drugController = new DrugController();
    public static boolean isStopped = false;

    public static void main(String[] args) throws InterruptedException {
        Thread pharmacy = new Thread(new Pharmacy());
        Thread man = new Thread(new Person(), "Man");
        Thread woman = new Thread(new Person(), "Woman");

        pharmacy.start();
        man.start();
        woman.start();

        Thread.sleep(1000);
        isStopped = true;
    }

    public static class Pharmacy implements Runnable {

        @Override
        public void run() {
            while (!isStopped) {
                drugController.buy(getRandomDrug(), getRandomCount());
                for (int i = 0; i < 3; i++) {
                    waitAMoment();
                }
            }
        }
    }

    public static class Person implements Runnable {

        @Override
        public void run() {
            while (!isStopped) {
                drugController.sell(getRandomDrug(), getRandomCount());
                waitAMoment();
            }
        }
    }

    public static int getRandomCount() {
        return (int) (Math.random() * 3) + 1;
    }

    public static Drug getRandomDrug() {
        int index = (int) ((Math.random() * 1000) % (drugController.allDrugs.size()));
        List<Drug> drugs = new ArrayList<>(drugController.allDrugs.keySet());
        return drugs.get(index);
    }

    private static void waitAMoment() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
    }
}
