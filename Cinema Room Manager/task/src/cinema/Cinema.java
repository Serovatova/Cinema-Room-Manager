package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    private static int rows;
    private static int seats;
    private static int purchaseSeries;
    private static int placeOfPurchase;
    private static char[][] cinemaHall;
    private static final char CHAR_FREE = 'S';
    private static final char CHAR_PURCHASE = 'B';
    private static final String ENTER_ROWS = "Enter the number of rows:";
    private static final String ENTER_SEATS = "Enter the number of seats in each row:";
    private static final String MENU = "Menu";
    private static final String SHOW_THE_SEATS = "1. Show the seats";
    private static final String BUY_THE_TICKET = "2. Buy a ticket";
    private static final String STATISTICS = "3. Statistics";
    private static final String EXIT = "0. Exit";
    private static final String TICKETS = "Number of purchased tickets: %d";
    private static final String PERCENT = "Percentage: %.2f%%";
    private static final String CURRENT_INCOME = "Current income: $%d";
    private static final String TOTAL_INCOME = "Total income: $%d";
    private static int counterBuyTicked = 0;
    private static final String ALREADY_PURCHASE = "That ticket has already been purchased";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(ENTER_ROWS);
        rows = scanner.nextInt();
        System.out.println(ENTER_SEATS);
        seats = scanner.nextInt();

        initCinema(rows, seats);
        System.out.println();

        printMenu();
    }

    public static void initCinema(int rows, int seats) {
        cinemaHall = new char[rows][seats];
        for (char[] chars : cinemaHall) {
            Arrays.fill(chars, CHAR_FREE);
        }
    }

    public static void printCinema() {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 0; i < cinemaHall[0].length; i++) {
            System.out.print(i + 1 + " ");
        }

        for (int i = 0; i < cinemaHall.length; i++) {
            System.out.println();
            System.out.print(i + 1 + " ");
            for (int j = 0; j < cinemaHall[i].length; j++) {
                System.out.print(cinemaHall[i][j] + " ");
            }
        }
        System.out.println();
    }

    private static void printMenu() {
        boolean menu = true;
        while (menu) {
            System.out.println(MENU);
            System.out.println(SHOW_THE_SEATS);
            System.out.println(BUY_THE_TICKET);
            System.out.println(STATISTICS);
            System.out.println(EXIT);

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> printCinema();
                case 2 -> buyTicket();
                case 3 -> printStatistic();
                case 0 -> menu = false;
            }
            System.out.println();
        }
    }

    private static void buyTicket() {
        while (true) {
            System.out.println("Enter a row number:");
            purchaseSeries = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            placeOfPurchase = scanner.nextInt();

            if (isValidSeat(cinemaHall, purchaseSeries, placeOfPurchase)) {
                if (cinemaHall[purchaseSeries - 1][placeOfPurchase - 1] == CHAR_FREE) {
                    changeCharacterOnPurchase();
                    counterBuyTicked += calculateTicketPrices();
                    System.out.println();
                    System.out.println("Ticket price: $" + calculateTicketPrices());
                    break;
                } else {
                    System.out.println(ALREADY_PURCHASE);
                }
            } else {
                System.out.println("Wrong input!");
            }
        }
    }

    //рассчет стоимости билета
    private static int calculateTicketPrices() {
        int ticketPrice = 0;
        if (rows * seats <= 60) {
            ticketPrice = 10;
        } else if (purchaseSeries <= cinemaHall.length / 2) {
            ticketPrice = 10;
        } else {
            ticketPrice = 8;
        }
        return ticketPrice;
    }

    private static void changeCharacterOnPurchase() {
        cinemaHall[purchaseSeries - 1][placeOfPurchase - 1] = CHAR_PURCHASE;
    }

    private static void printStatistic() {
        System.out.println(TICKETS.formatted(getPurchasedTicked()));
        System.out.println(PERCENT.formatted(getPercent(getPurchasedTicked())));
        System.out.println(CURRENT_INCOME.formatted(counterBuyTicked));
        System.out.println(TOTAL_INCOME.formatted(revenueCalculation(rows, seats)));
    }

    private static int getPurchasedTicked() {
        int counter = 0;

        for (int i = 0; i < cinemaHall.length; i++) {
            for (int j = 0; j < cinemaHall[i].length; j++) {
                if (cinemaHall[i][j] == CHAR_PURCHASE) {
                    counter++;
                }
            }
        }
        return counter;
    }

    //рассчет стоимости купленных билетов в процентах
    private static double getPercent(int tickets) {
        double oneTicketPercent = 100.0 / (rows * seats);
        return tickets * oneTicketPercent;
    }

    private static int revenueCalculation(int rows, int seats) {
        final int SMALL_TICKET = 10;
        final int LARGE_TICKET = 8;
        int totalSeats = rows * seats;
        int totalIncome = 0;
        int half = rows / 2;

        if (totalSeats > 60) {
            if (rows % 2 == 0) {
                totalIncome = half * seats * LARGE_TICKET + half * seats * SMALL_TICKET;
            } else {
                totalIncome = (rows - half) * seats * LARGE_TICKET + half * seats * SMALL_TICKET;
            }
        } else {
            totalIncome = SMALL_TICKET * totalSeats;
        }
        return totalIncome;
    }


    private static boolean isValidSeat(char[][] array, int row, int col) {
        return row > 0 && col > 0 && row <= array.length && col <= array[0].length;
    }
}