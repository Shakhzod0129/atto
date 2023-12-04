package uz.code.service;

import uz.code.enums.CardStatus;
import uz.code.enums.TerminalStatus;
import uz.code.enums.TransactionType;
import uz.code.model.Card;
import uz.code.model.Terminal;
import uz.code.model.Transaction;
import uz.code.repository.CardRepository;
import uz.code.repository.TerminalRepository;
import uz.code.repository.TransactionRepository;

import java.util.List;

public class TerminalService {
    static TerminalRepository terminalRepository=new TerminalRepository();
    static TransactionRepository transactionRepository=new TransactionRepository();
    static CardRepository cardRepository=new CardRepository();
    static boolean bool=false;

    public static void changeStatus(String number, TerminalStatus status) {
        List<Terminal> terminalList=terminalRepository.getAll();

        for (Terminal terminal : terminalList) {
            if(terminal.getCode().equals(number)){
                terminal.setStatus(status);
              bool=  terminalRepository.changeStatus(number,status);
            }
        }

        if(bool){
            System.out.println("Success ✅");
        }else {
            System.out.println("Error❌");
        }
    }

    public void create(Terminal terminal) {
        terminal.setStatus(TerminalStatus.ACTIVE);
       bool= terminalRepository.create(terminal);

       if(bool){
           System.out.println("terminal has created successfully✅");
       }else {
           System.out.println("Error❌");
       }
    }

    public void list() {

        List<Terminal> terminalList=terminalRepository.getAll();

        for (Terminal terminal1 : terminalList) {
            System.out.println(terminal1);
        }

    }


    public void payment(Transaction transaction, String number, String terminalNumber) {
        List<Terminal> terminalList=terminalRepository.getAll();
        List<Card> cardList=cardRepository.getAll(CardStatus.ACTIVE);
        double summa=1700;
        for (Terminal terminal : terminalList) {
            if(terminal.getCode().equals(terminalNumber)&&terminal.getStatus().equals(TerminalStatus.ACTIVE)){
                for (Card card : cardList) {
                    if(card.getNumber().equals(number)){
                        if(card.getBalance()>=summa){

                            card.setBalance(card.getBalance()-summa);
                            cardRepository.payFromCardToCard(card,number,summa);
                            transaction.setType(TransactionType.PAYMENT);
                            transaction.setTerminalCode(terminal.getCode());
                            bool= transactionRepository.payment(transaction,card,terminal,summa);

                        }else {
                            System.out.println("Your balance is not enough to pay❌");
                            return;
                        }
                    }
                }
            }
        }

        if(bool){
            System.out.println("Success✅");
        }else {
            System.out.println("Error❌");
        }
    }


    public void update(String oldCode, String newAddress) {
        List<Terminal> terminalList=terminalRepository.getAll();

        for (Terminal terminal : terminalList) {
            if(terminal.getCode().equals(oldCode)){
                terminal.setAddress(newAddress);
                bool=terminalRepository.update(terminal,oldCode);
            }
        }

        if(bool){
            System.out.println("terminal has updated successfully✅");
        }else {
            System.out.println("Error❌");
        }
    }

    public void delete(String number) {
        List<Terminal> terminalList=terminalRepository.getAll();
        for (Terminal terminal : terminalList) {
            if(terminal.getCode().equals(number)){
                terminal.setStatus(TerminalStatus.BLOCKED);
                bool=terminalRepository.delete(terminal,number);
            }
        }
        if(bool){
            System.out.println("Success✅");
        }else {
            System.out.println("Error❌");
        }
    }
}
// if(card.getBalance()>summa){
//         card.setBalance(card.getBalance()-summa);
//         transaction.setType(TransactionType.PAYMENT);
//         transaction.setTerminalCode(terminal.getCode());
//         transactionRepository.payment(card,terminal.getCode(),summa);
//         }