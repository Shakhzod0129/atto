package uz.code.controller;

import uz.code.enums.CardStatus;
import uz.code.enums.ProfileStatus;
import uz.code.enums.TerminalStatus;
import uz.code.model.Profile;
import uz.code.service.TerminalService;
import uz.code.utils.ScannerUtils;

import java.time.LocalDate;

import static uz.code.controller.Controller.*;

public class AdminController {

    public static void adminMenu() {

        while (true) {
            System.out.println("""
                    1.Card.
                    2.Terminal.
                    3.Profile.
                    4.Transaction.
                    5.Statistics.
                    0.Exit.""");
            int n = ScannerUtils.nextInt("Choose option : ");

            switch (n) {
                case 1 -> cardMenuForAdmin();
                case 2 -> terminalMenu();
                case 3 -> profileMenu();
                case 4 -> transactionMenu();
                case 5 -> statistics();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Choose the right option!");
            }

        }

    }

    private static void statistics() {
        while (true) {
            System.out.println("""
                    ========================
                    1.Today's payments.
                    2.Daily payments.
                    3.Payments between two days.
                    4.Total balance.
                    5.Transaction by Terminal.
                    6.Transaction by Card.
                    0.Exit.\n""");
            int n = ScannerUtils.nextInt("Choose option : ");
            switch (n) {
                case 1 -> todayPayments();
                case 2 -> dailyPayments();
                case 3 -> paymentsBetweenDays();
                case 4 -> totalBalance();
                case 5 -> transactionByTerminal();
                case 6 -> transactionByCard();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Choose the right option!");
            }

        }
    }

    private static void transactionByCard() {
        String cardNumber = scanner.nextLine("Enter Card number : ");

        transactionService.transactionByCard(cardNumber);
    }


    private static void transactionByTerminal() {
        String code = scanner.nextLine("Enter Code of Terminal : ");

        transactionService.transactionByTerminal(code);

    }

    private static void totalBalance() {
        cardService.balanceCompanyCard();

    }

    private static void paymentsBetweenDays() {

    }

    private static void dailyPayments() {
        String date = scanner.nextLine("Enter day [ yyyy-MM-dd] : ");

        transactionService.dailyPayments(date);
    }

    private static void todayPayments() {

        transactionService.todayPayments();
    }

    private static void cardMenuForAdmin() {
        while (true) {
            System.out.println("""
                    ========================
                    1.Create Card.
                    2.List of Card.
                    3.Update Card.
                    4.Change Card Status.
                    5.Delete Card.
                    0.Exit.\n""");
            int n = ScannerUtils.nextInt("Choose option : ");
            switch (n) {
                case 1 -> createCard();
                case 2 -> cardService.list();
                case 3 -> updateCard();
                case 4 -> cardChangeStatusByAdmin();
                case 5 -> deleteCard();
                case 0 -> {
                    return;

                }
                default -> System.out.println("Choose the right option!");
            }

        }
    }

    private static void terminalMenu() {
        while (true) {
            System.out.println("""
                    ========================
                    1.Create Terminal.
                    2.List of Terminal.
                    3.Update Terminal.
                    4.Change Terminal Status.
                    5.Delete Terminal.
                    0.Exit.\n""");
            int n = ScannerUtils.nextInt("Choose option : ");
            switch (n) {
                case 1 -> createTerminal();
                case 2 -> terminalService.list();
                case 3 -> updateTerminal();
                case 4 -> terminalChangeStatusByAdmin();
                case 5 -> deleteTerminal();
                case 0 -> {
                    return;

                }
                default -> System.out.println("Choose the right option!");
            }

        }
    }

    private static void deleteTerminal() {
        String number = scanner.nextLine("Enter the code of terminal to delete status");

        terminalService.delete(number);

    }

    private static void terminalChangeStatusByAdmin() {
        String number = scanner.nextLine("Enter the code of terminal to edit status");

        System.out.println("""
                1.ACTIVE
                2.INACTIVE
                3.BLOCKED
                0.EXIT""");
        String option = scanner.nextLine("Choose option : ");
        TerminalStatus status = null;

        switch (option) {
            case "1" -> status = TerminalStatus.ACTIVE;
            case "2" -> status = TerminalStatus.INACTIVE;
            case "3" -> status = TerminalStatus.BLOCKED;
            case "0" -> {
                return;
            }
            default -> System.out.println("You have chosen wrong option❌");
        }


        TerminalService.changeStatus(number, status);

    }

    private static void updateTerminal() {

        String code = scanner.nextLine("Enter code terminal code : ");

        String newAddress = scanner.nextLine("Enter new address to update the terminal : ");

        terminalService.update(code,newAddress);
    }

    private static void createTerminal() {
        String code = scanner.nextLine("Enter unique code for terminal : ");
        String address = scanner.nextLine("Enter address for terminal : ");

        terminal.setCode(code);
        terminal.setAddress(address);

        terminalService.create(terminal);


    }

    private static void profileMenu() {
        while (true) {
            System.out.println("""
                    ========================
                    1.Profile List.
                    2.Change status of profile.
                    0.Exit.\n""");
            int n = ScannerUtils.nextInt("Choose option : ");
            switch (n) {
                case 1 -> listOfProfile();
                case 2 -> changeStatusProfile();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Choose the right option!");
            }

        }
    }

    private static void changeStatusProfile() {
        String number = scanner.nextLine("Enter the phone of profile to edit status");

        System.out.println("""
                1.ACTIVE
                2.INACTIVE
                3.BLOCKED
                0.EXIT""");
        String option = scanner.nextLine("Choose option : ");
        ProfileStatus status = null;

        switch (option) {
            case "1" -> status = ProfileStatus.ACTIVE;
            case "2" -> status = ProfileStatus.INACTIVE;
            case "3" -> status = ProfileStatus.BLOCKED;
            case "0" -> {
                return;
            }
            default -> System.out.println("You have chosen wrong option❌");
        }


        profileService.changeStatus(number, status);
    }

    private static void listOfProfile() {
        profileService.listOfUser();
    }

    private static void transactionMenu() {
        while (true) {
            System.out.println("""
                    ========================
                    1.Transaction List.
                    2.Company card balance.
                   
                    0.Exit.\n""");
            int n = ScannerUtils.nextInt("Choose option : ");
            switch (n) {
                case 1 -> listOfTransactions();
                case 2 -> companyCardBalance();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Choose the right option!");
            }

        }
    }

    private static void companyCardBalance() {
        cardService.balanceCompanyCard();
    }

    private static void listOfTransactions() {
        transactionService.transactions();
    }

    private static void createCard() {
        System.out.println("Create card by admin");

        String number = scanner.nextLine("Enter unique number for new card : ");
        String expDate = scanner.nextLine("Enter expiry date : [ yyyy-MM-dd]");

        card.setNumber(number);
        card.setExpDate(LocalDate.parse(expDate));

        cardService.create(card);

    }

    private static void updateCard() {
        System.out.println("update card by admin");
        String oldNumber = scanner.nextLine("Enter the number of card to update : ");
        String newNumber = scanner.nextLine("Enter a new  unique number to update : ");
        String newExpDate = scanner.nextLine("Enter a new expiry date : [ yyyy-MM-dd]");

        cardService.update(oldNumber, newNumber, newExpDate);


    }

    private static void cardChangeStatusByAdmin() {
        String number = scanner.nextLine("Enter the number of card to edit status");

        System.out.println("""
                1.ACTIVE
                2.INACTIVE
                3.BLOCKED
                0.EXIT""");
        String option = scanner.nextLine("Choose option : ");
        CardStatus status = null;

        switch (option) {
            case "1" -> status = CardStatus.ACTIVE;
            case "2" -> status = CardStatus.INACTIVE;
            case "3" -> status = CardStatus.BLOCKED;
            case "0" -> {
                return;
            }
            default -> System.out.println("You have chosen wrong option❌");
        }


        cardService.changeStatus(number, status);

    }
}
