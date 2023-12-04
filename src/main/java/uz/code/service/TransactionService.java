package uz.code.service;

import uz.code.enums.CardStatus;
import uz.code.enums.TransactionType;
import uz.code.model.Card;
import uz.code.model.Profile;
import uz.code.model.Transaction;
import uz.code.repository.CardRepository;
import uz.code.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalInt;

public class TransactionService {
    TransactionRepository transactionRepository=new TransactionRepository();
    CardRepository cardRepository=new CardRepository();
    CardService cardService=new CardService();
    public void fillBalanceByUser(Transaction transaction, Profile profile, String number, double summa) {
        List<Card> cardList = cardRepository.getAll(CardStatus.ACTIVE);
        boolean bool=false;
        for (Card card : cardList) {
            if(card.getNumber().equals(number)){
                if(card.getPhone().equals(profile.getPhone())){
                    card.setBalance(card.getBalance()+summa);
                    transaction.setType(TransactionType.REFILL);
                    transactionRepository.fillBalance(transaction,number,summa);
                    bool=  cardRepository.fillBalance(profile,number, card.getBalance());
                }

            }
        }

        if(bool){
            System.out.println("Card balance has filled successfully✅");
        }else {
            System.out.println("Card has not found❌");
        }

    }

    public void transactions(Profile profile) {
        List<Card> cardList=cardRepository.getAll(CardStatus.ACTIVE);

        for (Card card : cardList) {
            if(card.getPhone().equals(profile.getPhone())){
                for (Transaction transaction : transactionRepository.getAll()) {
                    if(transaction.getCardNumber().equals(card.getNumber())){
                        System.out.println(transaction);
                    }
                }
            }
        }

    }
    public void transactions() {

        for (Transaction transaction : transactionRepository.getAll()) {
            System.out.println(transaction);
        }
    }

    public void todayPayments() {
        List<Transaction> transactionList=transactionRepository.getAll();
        LocalDate today=LocalDate.now();
        int year1 = today.getYear();
        int monthValue1 = today.getMonthValue();
        int dayOfMonth1 = today.getDayOfMonth();

        for (Transaction transaction : transactionList) {
            int year = transaction.getCreatedDate().getYear();
            int monthValue = transaction.getCreatedDate().getMonthValue();
            int dayOfMonth = transaction.getCreatedDate().getDayOfMonth();
            if(year==year1&&monthValue==monthValue1&&dayOfMonth==dayOfMonth1){
                System.out.println(transaction);
            }

        }
    }


    public void dailyPayments(String date) {
        LocalDate day = LocalDate.parse(date);
        int year1 = day.getYear();
        int monthValue1 = day.getMonthValue();
        int dayOfMonth1 = day.getDayOfMonth();

        List<Transaction> transactionList=transactionRepository.getAll();

        for (Transaction transaction : transactionList) {
            int year = transaction.getCreatedDate().getYear();
            int monthValue = transaction.getCreatedDate().getMonthValue();
            int dayOfMonth = transaction.getCreatedDate().getDayOfMonth();
            if(year==year1&&monthValue==monthValue1&&dayOfMonth==dayOfMonth1){
                System.out.println(transaction);
            }else {
                System.out.println("Error❌");
                return;
            }
        }

    }

    public void transactionByTerminal(String terminalCode) {
        List<Transaction> transactionList=transactionRepository.getAll();

        for (Transaction transaction : transactionList) {
            if(transaction.getTerminalCode() != null){
                if(transaction.getTerminalCode().equals(terminalCode)){
                    System.out.println(transaction);
                }else {
                    System.out.println("Terminal has not found with this Code❌");
                    return;
                }
            }

        }

    }

    public void transactionByCard(String cardNumber) {
        List<Transaction> transactionList=transactionRepository.getAll();
        List<Card> cardList=cardRepository.getAll();

//        for (Card card : cardList) {
//            if(card.getNumber().equals(cardNumber)){
                for (Transaction transaction : transactionList) {
                    if(transaction.getCardNumber().equals(cardNumber)){
                        System.out.println(transaction);
                    }else {
                        System.out.println("Card has not found with this Number❌");
                        return;

                    }
                }
//            }
//        }


    }
}
