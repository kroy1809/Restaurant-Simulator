# Restaurant Simulation
Use of Java synchronization primitives to simulate the operations in a restaurant


## Problem Statement

Restaurant 6431 is now open. A restaurant requires careful coordination of resources,
which are the tables in the restaurant, the cooks available to cook the order, and the machines
available to cook the food (we assume that there is no contention on other possible resources,
like servers to take orders and serve the orders).

Eaters in the restaurant place their orders when they get seated. These orders are then handled
by available cooks. Each cook handles one order at a time. A cook handles an order by using
machines to cook the food items. To simplify the simulation, we make the following
assumptions. There are only three types of food served by the restaurant - a Buckeye Burger,
Brutus Fries, and Coke. Each person entering the restaurant occupies one table and orders one
or more burgers, zero or more orders of fries, and zero or one glass of coke. The cook needs to
use the burger machine for 5 minutes to prepare each burger, fries machine for 3 minutes for
one order of fries, and the soda machine for 1 minute to fill a glass with coke. The cook can use
at most one of these three machines at any given time. There is only one machine of each type
in the restaurant (so, for example, over a 5-minute duration, only one burger can be prepared).
Once the food (all items at the same time) are brought to a diner's table, they take exactly 30
minutes to finish eating them, and then leave the restaurant, making the table available for
another diner (immediately).


## Input format

Diners arrive at the restaurant over a 120 minute duration, which is taken as input by
the simulation. If there are N diners arriving, the input has N+3 lines, specifying, in order:
number of diners (N), number of tables, number of cooks, and N other lines each with the
following four numbers: a number between 0 and 120 stating when the diner arrived (this
number is increasing across lines), the number of burgers ordered (1 or higher), number of
order of fries (0 or higher), and whether or not coke was ordered (0 or 1).


## Output

Simulation should output when each diner was seated, which table they were seated in,
which cook processed their order, when each of the machines was used for their orders, and
when the food was brought to their table. Finally, you should state the time when the last diner
leaves the restaurant. To reduce the length of an experiment, I have used one second to
simulate one minute.


## Execution

1. Navigate to the directory where you have unzipped the files
2. Place the input file you want the code to run on in the directory
3. Run `make rebuild`
4. Run `make run FILE=input_file.txt`
5. The output will be displayed in the command line and written to a file (output.txt) as well


## Assumptions

1. If either of the following is zero, it is considered invalid input - number of diners, number of tables, number of cooks.
2. If the number of diners does not match with number of lines, it is also an invalid input.
3. It is assumed that the restaurant operates for 120 minutes and hence any diner who arrives after that is not served.
4. It is assumed that the number of burgers ordered (1 or higher), number of order of fries (0 or higher), whether or not coke was ordered (0 or 1).
5. A FIFO scheme has been setup to serve the diners instead of randomizing the order.