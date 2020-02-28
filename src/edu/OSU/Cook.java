package edu.OSU;

public class Cook implements Runnable{
    public int cookId;
    public Diner servedDiner;
    public int numBurgers, numFries, coke;

    public void serveDiner() throws InterruptedException {
        synchronized (App.hungryDiners) {
            while (App.hungryDiners.isEmpty())
                App.hungryDiners.wait();
            servedDiner = App.hungryDiners.poll();
            numBurgers = servedDiner.order.burgers_num;
            numFries = servedDiner.order.fries_num;
            coke = servedDiner.order.coke;
            System.out.println(App.getTime() +" - Cook "+cookId+" processes Diner "+servedDiner.diner_id+"'s order.");
            App.out.println(App.getTime() +" - Cook "+cookId+" processes Diner "+servedDiner.diner_id+"'s order.");
        }
    }

    public void cook() throws InterruptedException {
        while ((App.isBurgerMachineFree && numBurgers>0) || (App.isFriesMachineFree && numFries>0) ||
                (App.isCokeMachineFree && coke==1)) {
            if (App.isBurgerMachineFree && numBurgers>0) {
                synchronized (App.burgerMachineLock) {
                    App.isBurgerMachineFree = false;
                    System.out.println(App.getTime() + " - Cook " + cookId + " is using the burger machine.");
                }
                Thread.sleep(5000);
                synchronized (App.burgerMachineLock) {
                    --numBurgers;
                    App.isBurgerMachineFree = true;
                    App.burgerMachineLock.notify();
                }
            }

            if (App.isFriesMachineFree && numFries>0) {
                synchronized (App.friesMachineLock) {
                    App.isFriesMachineFree = false;
                    System.out.println(App.getTime() + " - Cook " + cookId + " is using the fries machine.");
                }
                Thread.sleep(3000);
                synchronized ((App.friesMachineLock)) {
                    --numFries;
                    App.isFriesMachineFree = true;
                    App.friesMachineLock.notify();
                }
            }

            if (App.isCokeMachineFree && coke>0) {
                synchronized (App.cokeMachineLock) {
                    App.isCokeMachineFree = false;
                    System.out.println(App.getTime() + " - Cook " + cookId + " is using the coke machine.");
                }
                Thread.sleep(1000);
                synchronized (App.cokeMachineLock) {
                    --coke;
                    App.isCokeMachineFree = true;
                    App.cokeMachineLock.notify();
                }
            }


            if (!App.isBurgerMachineFree && numBurgers>0) {
                synchronized (App.burgerMachineLock) {
                    App.burgerMachineLock.wait();
                }
            }
            if (!App.isFriesMachineFree && numFries>0) {
                synchronized (App.friesMachineLock) {
                    App.friesMachineLock.wait();
                }
            }
            if (!App.isCokeMachineFree && coke==1) {
                synchronized (App.cokeMachineLock) {
                    App.cokeMachineLock.wait();
                }
            }
        }
    }

    public void orderReady() {
        if ( (numBurgers + numFries + coke) == 0 ) {
            synchronized (servedDiner) {
                servedDiner.notify();
            }
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                serveDiner();
                cook();
                orderReady();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}