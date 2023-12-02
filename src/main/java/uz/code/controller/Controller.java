package uz.code.controller;

import uz.code.db.DataBase;
import uz.code.enums.CardStatus;
import uz.code.enums.UserRole;
import uz.code.model.Card;
import uz.code.model.Profile;
import uz.code.model.Terminal;
import uz.code.model.Transaction;
import uz.code.service.CardService;
import uz.code.service.ProfileService;
import uz.code.service.TerminalService;
import uz.code.service.TransactionService;
import uz.code.utils.ScannerUtils;

import java.time.LocalDate;
import java.util.Scanner;

public class Controller {

    static ScannerUtils scanner = new ScannerUtils();
    static Scanner s=new Scanner(System.in);
    static CardService cardService = new CardService();
    static ProfileService profileService = new ProfileService();
    static TerminalService terminalService = new TerminalService();
    static TransactionService transactionService = new TransactionService();

    static Profile profile = new Profile();
    static Card card = new Card();
    static Terminal terminal = new Terminal();
    static Transaction transaction = new Transaction();


    public static void start() {

        DataBase dataBase = new DataBase();
        dataBase.createUserTable();
        dataBase.createCardTable();
        dataBase.createTerminalTable();
        dataBase.createTransactionTable();
        int n = 0;

        while (true) {

            menu();
                n = getAction();
                switch (n) {
                    case 1 -> login();
                    case 2 -> signUp();
                    default -> System.out.println("You have chose wrong option❌");
                }
            }
    }

    private static void signUp() {
        String name = scanner.nextLine("Enter your name : ");
        String surname = scanner.nextLine("Enter your surname : ");
        String phone = scanner.nextLine("Enter your phone : ");
        String password = scanner.nextLine("Enter your password : ");

        profile.setName(name);
        profile.setSurname(surname);
        profile.setPhone(phone);
        profile.setPassword(password);

        profileService.create(profile);


    }

    private static void login() {
        String phone = scanner.nextLine("Enter your phone number : ");
        String password = scanner.nextLine("Enter your password : ");

        boolean login = profileService.login(phone, password);

        if (login) {
            for (Profile profile1 : profileService.list()) {
                if (profile1.getPhone().equals(phone) && profile1.getPassword().equals(password)) {
                    profile.setPhone(phone);
                    if (profile1.getRole().equals(UserRole.USER)) {
                        userMenu();
                    } else {
                        adminMenu();
                    }
                }

            }
        }


    }

    private static void adminMenu() {
        int n;
        while (true) {
            System.out.println("""
                    1.Card.
                    2.Terminal.
                    3.Profile.
                    4.Transaction.
                    0.Exit.""");
            n = getAction();

            switch (n) {
                case 1 -> cardMenuForAdmin();
                case 2 -> terminalMenu();
                case 3 -> profileMenu();
                case 4 -> transactionMenu();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Choose the right option!");
            }

        }

    }

    private static void transactionMenu() {
        System.out.println("transaction menu");
    }

    private static void profileMenu() {
        System.out.println("user menu");
    }

    private static void terminalMenu() {
        System.out.println("terminal menu");
    }

    private static void cardMenuForAdmin() {
        int n;
        while (true) {
            System.out.println("""
                    ========================
                    1.Create Card.
                    2.List of Card.
                    3.Update Card.
                    4.Change Card Status.
                    5.Delete Card.
                    0.Exit.\n""");
            n = getAction();

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

    private static void updateCard() {
        System.out.println("update card by admin");
        String oldNumber = scanner.nextLine("Enter the number of card to update : ");
        String newNumber = scanner.nextLine("Enter a new  unique number to update : ");
        String newExpDate = scanner.nextLine("Enter a new expiry date : [ yyyy-MM-dd]");

        cardService.update(oldNumber, newNumber, newExpDate);


    }

    private static void createCard() {
        System.out.println("Create card by admin");

        String number = scanner.nextLine("Enter unique number for new card : ");
        String expDate = scanner.nextLine("Enter expiry date : [ yyyy-MM-dd]");

        card.setNumber(number);
        card.setExpDate(LocalDate.parse(expDate));

        cardService.create(card);

    }

    private static void userMenu() {

        int n;
        while (true) {
            System.out.println("""
                    1.My Card
                    2.ReFill.
                    3.Transactions
                    4.Make Payment
                    0.Exit.""");
            n = getAction();

            switch (n) {
                case 1 -> cardMenuForUser();
                case 2 -> reFill();
                case 3 -> transactionService.transactions();
                case 4 -> makePayment();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Choose the right option!");
            }


        }
    }

    private static void makePayment() {
        System.out.println("Payment");
    }

    private static void cardMenuForUser() {
        int n;
        while (true) {
            System.out.println("""
                    ========================
                    1.Add Card.
                    2.List of Card.
                    3.Change Card Status.
                    4.Delete Card.
                    0.Exit.\n""");

            n = getAction();

            switch (n) {
                case 1 -> addCard();
                case 2 -> listOfUser();
                case 3 -> cardChangeStatusByUser();
                case 4 -> deleteCard();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Choose the right option!");
            }


        }
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


        cardService.changeStatusByUser(number, status);
    }

    private static void listOfUser() {

        cardService.listOfUser(profile);
    }

    private static void reFill() {
        String number = scanner.nextLine("Enter the number of card to edit status : ");
        System.out.println("Enter amount of money : ");
        double summa=s.nextDouble();


        cardService.fillBalance(profile,number,summa);


    }

    private static void deleteCard() {
        String number = scanner.nextLine("Enter the number of the card to delete card");

        cardService.delete(number);
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

    private static void addCard() {
        String number = scanner.nextLine("Please! Enter your card number : ");


//        card.setPhone(profile.getPhone());
        cardService.addCardForUser(profile, number);

    }

    private static void menu() {

        System.out.println("""
                1.Login
                2.Sign up
                """);

    }

    private static int getAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose menu: ");
        return scanner.nextInt();
    }
}
