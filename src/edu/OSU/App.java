package edu.OSU;


import java.io.*;
import java.util.*;

public class App {
    public static long start;
    public static int tables;
    public static int diners, cooks;

    public static Object burgerMachineLock, friesMachineLock, cokeMachineLock;
    public static boolean isBurgerMachineFree, isFriesMachineFree,isCokeMachineFree;
    public static PrintWriter out;

    static final Queue<Integer> emptyTables = new LinkedList<>();
    static Queue<Diner> allDiners = new LinkedList<>();
    static final Queue<Diner> hungryDiners = new LinkedList<>();


    public static void main(String[] args) throws IOException, InterruptedException {
        String file = System.getProperty("user.dir")+"//input//" + args[0] + ".txt";
        String outfile = System.getProperty("user.dir") + "//output//" + args[1] + ".txt";
        BufferedReader br = new BufferedReader(new FileReader(file));

        diners = Integer.parseInt(br.readLine());
        tables = Integer.parseInt(br.readLine());
        cooks = Integer.parseInt(br.readLine());

        if (diners==0 || tables==0 || cooks==0) {
            System.out.println("Invalid input");
            System.exit(0);
        }

        for (int i = 1; i<= tables; i++) emptyTables.add(i);

        for (int i=0; i<diners; i++) {
            String [] dinerInfo = br.readLine().split(",");
            if (dinerInfo.length>4) {
                System.out.println("Invalid input");
                System.exit(0);
            }

            int arrivalTime = Integer.parseInt(dinerInfo[0].trim());
            int burgersNum = Integer.parseInt(dinerInfo[1].trim());
            int friesNum = Integer.parseInt(dinerInfo[2].trim());
            int cokeNum = Integer.parseInt(dinerInfo[3].trim());

            Order ord = new Order();
            ord.burgers_num = Math.max(burgersNum, 0);
            ord.fries_num = Math.max(friesNum, 0);
            ord.coke = cokeNum <= 0 ? 0 : 1;

            Diner newDiner = new Diner();
            newDiner.diner_id = i+1;
            newDiner.arrivalTime = arrivalTime;
            newDiner.order = ord;

            allDiners.add(newDiner);
        }

        if (allDiners.size()!=diners) {
            System.out.println("Invalid input");
            System.exit(0);
        }

        burgerMachineLock = new Object();
        isBurgerMachineFree = true;

        friesMachineLock = new Object();
        isFriesMachineFree = true;

        cokeMachineLock = new Object();
        isCokeMachineFree = true;

        out = new PrintWriter(outfile);

        start = System.currentTimeMillis();

        System.out.println(getTime()+" - Restaurant 6431 is now open.");
        out.println(getTime()+" - Restaurant 6431 is now open.");
        int i = 1;
        int prevArrival = 0, waitTime;

        while(i<=cooks) {
            Cook cook = new Cook();
            cook.cookId = i;
            Thread cookThread = new Thread(cook);
            cookThread.start();
            i++;
        }

        i = 0;
        while(i<diners) {
            Diner arrivedDiner = allDiners.poll();
            waitTime = arrivedDiner.arrivalTime - prevArrival;
            Thread dinerThread = new Thread(arrivedDiner);
            Thread.sleep(waitTime*1000);
            dinerThread.start();
            prevArrival = arrivedDiner.arrivalTime;
            i++;
        }
    }

    public static String getTime() {
        long currTime = (System.currentTimeMillis()-start)/1000;
        String hrs, mins;
        if (currTime < 60) {
            hrs = "00";
            mins = String.format("%02d", currTime);
        }
        else {
            hrs = String.format("%02d", currTime / 60);
            mins = String.format("%02d", currTime - ((currTime / 60) * 60));
        }
        return hrs+":"+mins;
    }
}
