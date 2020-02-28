package edu.OSU;

public class Diner implements Runnable{
    public int diner_id;
    public int arrivalTime;
    public int tableAlloted;
    public Order order;

    public void arrive() {
        System.out.println(App.getTime()+" - Diner " + diner_id + " arrives.");
    }

    public void seated() throws InterruptedException {
        synchronized (App.emptyTables) {
            while (App.emptyTables.isEmpty())
                App.emptyTables.wait();
            tableAlloted = App.emptyTables.poll();
            System.out.println(App.getTime() + " - Diner " + diner_id + " is seated at table " + tableAlloted +".");
            App.out.println(App.getTime() + " - Diner " + diner_id + " is seated at table " + tableAlloted +".");
        }
    }

    public void orders() {
        synchronized (App.hungryDiners) {
            App.hungryDiners.add(this);
            App.hungryDiners.notify();
        }
    }

    public void waitingForOrder() throws InterruptedException {
        synchronized (this) {
            this.wait();
        }
    }

    public void eating() throws InterruptedException {
        System.out.println(App.getTime()+" - Diner "+diner_id+"'s order is ready. Diner "+ diner_id + " starts eating.");
        App.out.println(App.getTime()+" - Diner "+diner_id+"'s order is ready. Diner "+ diner_id + " starts eating.");
        Thread.sleep(30000);
    }

    public void leave() {
        if (diner_id==App.diners) {
            System.out.println(App.getTime() +" - Diner "+ diner_id+" finishes. Diner "+diner_id+" leaves the restaurant.");
            App.out.println(App.getTime() +" - Diner "+ diner_id+" finishes. Diner "+diner_id+" leaves the restaurant.");
            System.out.println(App.getTime()+" - Last diner has left the restaurant.");
            App.out.println(App.getTime()+" - Last diner has left the restaurant.");
            App.out.close();
            System.exit(0);
        }
        else {
            System.out.println(App.getTime() +" - Diner "+ diner_id+" finishes. Diner "+diner_id+" leaves the restaurant.");
            App.out.println(App.getTime() +" - Diner "+ diner_id+" finishes. Diner "+diner_id+" leaves the restaurant.");
            synchronized (App.emptyTables) {
                App.emptyTables.add(tableAlloted);
                App.emptyTables.notify();
            }
        }
    }

    @Override
    public void run() {
        try {
            arrive();
            seated();
            orders();
            waitingForOrder();
            eating();
            leave();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
