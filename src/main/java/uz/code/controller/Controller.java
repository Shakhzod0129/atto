package uz.code.controller;

import uz.code.db.DataBase;
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

import java.util.Scanner;

public class Controller {

    static ScannerUtils scanner=new ScannerUtils();
    static CardService cardService=new CardService();
   static ProfileService profileService=new ProfileService();
   static TerminalService terminalService=new TerminalService();
   static TransactionService transactionService=new TransactionService();

   static Profile profile=new Profile();
   static Card card=new Card();
   static Terminal terminal=new Terminal();
   static Transaction transaction=new Transaction();


    public static void start(){

        DataBase dataBase=new DataBase();
        dataBase.createUserTable();
        dataBase.createCardTable();
        dataBase.createTerminalTable();
        dataBase.createTransactionTable();
        int n;

        while (true){

            menu();
            n=getAction();
            switch (n){
            case 1-> login();
            case 2-> signUp();
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

        if(login){
            for (Profile profile1 : profileService.list()) {
                if(profile1.getPhone().equals(phone)&&profile1.getPassword().equals(password)){
                    if(profile1.getRole().equals(UserRole.USER)){
                        userMenu();
                    }else {
                        adminMenu();
                    }
                }

            }
        }


    }

    private static void adminMenu() {
        int n;
        while (true){
            System.out.println("""
                    1.Card.
                    2.Terminal.
                    3.Profile.
                    4.Transaction.
                    0.Exit.""");
            n=getAction();

            switch (n){
                case 1-> cardMenuForAdmin();
                case 2-> terminalMenu();
                case 3-> profileMenu();
                case 4-> transactionMenu();
                case 0-> {
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
        while (true){
            System.out.println("""
                    ========================
                    1.Create Card.
                    2.List of Card.
                    3.Update Card.
                    4.Change Card Status.
                    5.Delete Card.
                    0.Exit.\n""");
            n=getAction();

            switch (n){
                case 1-> createCard();
                case 2-> cardService.list();
                case 3-> updateCard();
                case 4-> cardChangeStatus();
                case 5-> deleteCard();
                case 0-> {
                    return;

                }
                default -> System.out.println("Choose the right option!");
            }

        }
    }

    private static void updateCard() {
        System.out.println("update card by admin");

    }

    private static void createCard() {
        System.out.println("Create card by admin");
    }

    private static void userMenu() {

        int n;
        while (true){
            System.out.println("""
                    1.My Card
                    2.ReFill.
                    3.Transactions
                    4.Make Payment
                    0.Exit.""");
            n=getAction();

            switch (n){
                case 1-> cardMenuForUser();
                case 2-> reFill();
                case 3-> transactionService.transactions();
                case 4-> makePayment();
                case 0->{
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
        while (true){
            System.out.println("""
                    ========================
                    1.Add Card.
                    2.List of Card.
                    3.Change Card Status.
                    4.Delete Card.
                    0.Exit.\n""");

            n=getAction();

            switch (n){
                case 1-> createCard();
                case 2-> cardService.list();
                case 3-> updateCard();
                case 4-> cardChangeStatus();
                case 5-> deleteCard();
                case 0-> {
                    return;
                }
                default -> System.out.println("Choose the right option!");
            }


        }
    }

    private static void reFill() {
        System.out.println("refill");
    }

    private static void deleteCard() {
        System.out.println("delete card");
    }

    private static void cardChangeStatus() {
        System.out.println("change status");
    }

    private static void addCard() {
        System.out.println("add card");
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
