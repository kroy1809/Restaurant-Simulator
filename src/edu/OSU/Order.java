package edu.OSU;

public class Order {
    public int burgers_num;
    public int fries_num;
    public int coke;

    @Override
    public String toString() {
        return "Order: "+burgers_num+", "+fries_num+", "+coke;
    }
}
