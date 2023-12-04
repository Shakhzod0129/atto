package uz.code.controller;

import uz.code.db.DataBase;
import uz.code.enums.CardStatus;
import uz.code.enums.ProfileStatus;
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
import java.util.WeakHashMap;

public class Controller {
    static AuthControlller authControlller=new AuthControlller();

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


        while (true) {

            menu();
            int n = ScannerUtils.nextInt("Choose option : ");
            switch (n) {
                    case 1 -> authControlller.authenticationLogin();
                    case 2 -> authControlller.authenticationSignUp();
                    default -> System.out.println("You have chose wrong option❌");
                }
            }
    }

    static void deleteCard() {
        String number = scanner.nextLine("Enter the number of the card to delete card");

        cardService.delete(number);
    }

    private static void menu() {

        System.out.println("""
                1.Login
                2.Sign up
                """);

    }

}
