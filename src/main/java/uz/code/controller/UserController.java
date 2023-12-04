package uz.code.controller;

import uz.code.enums.CardStatus;
import uz.code.utils.ScannerUtils;

import static uz.code.controller.Controller.*;

public class UserController {

    public static void userMenu() {

        while (true) {
            System.out.println("""
                    1.My Card
                    2.ReFill.
                    3.Transactions
                    4.Make Payment
                    0.Exit.""");

            int n = ScannerUtils.nextInt("Choose option : ");
            switch (n) {
                case 1 -> cardMenuForUser();
                case 2 -> reFill();
                case 3 -> transactionService.transactions(profile);
                case 4 -> makePayment();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Choose the right option!");
            }


        }
    }

    private static void cardMenuForUser() {

        while (true) {
            System.out.println("""
                    ========================
                    1.Add Card.
                    2.List of Card.
                    3.Change Card Status.
                    4.Delete Card.
                    0.Exit.\n""");

            int n = ScannerUtils.nextInt("Choose option : ");

            switch (n) {
                case 1 -> addCard();
                case 2 -> listOfUserCard();
                case 3 -> cardChangeStatusByUser();
                case 4 -> deleteCard();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Choose the right option!");
            }


        }
    }

    private static void listOfCard() {
    }

    private static void reFill() {
        String number = scanner.nextLine("Enter the number of card to fill balance : ");
        System.out.println("Enter amount of money : ");
        double summa=s.nextDouble();

        transactionService.fillBalanceByUser(transaction,profile,number,summa);


    }

    private static void makePayment() {
        String number = scanner.nextLine("Enter the number of card to pay : ");
        String terminalNumber = scanner.nextLine("Enter the number of terminal to pay : ");

        terminalService.payment(transaction,number,terminalNumber);

    }

    private static void addCard() {
        String number = scanner.nextLine("Please! Enter your card number : ");



        cardService.addCardForUser(profile, number);

    }

    private static void cardChangeStatusByUser() {
        String number = scanner.nextLine("Enter the number of card to edit status");

        System.out.println("""
                1.ACTIVE
                2.INACTIVE
                0.EXIT""");
        String option = scanner.nextLine("Choose option : ");
        CardStatus status = null;

        switch (option) {
            case "1" -> status = CardStatus.ACTIVE;
            case "2" -> status = CardStatus.INACTIVE;
            case "0" -> {
                return;
            }
            default -> System.out.println("You have chosen wrong option❌");
        }


        cardService.changeStatusByUser(profile,number, status);
    }

    static void listOfUserCard() {

        cardService.listOfUserCard(profile);
    }
}
