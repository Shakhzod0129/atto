package uz.code.service;

import uz.code.enums.CardStatus;
import uz.code.model.Card;
import uz.code.model.Profile;
import uz.code.repository.CardRepository;

import java.time.LocalDate;
import java.util.List;

public class CardService {

    CardRepository cardRepository=new CardRepository();

    public void create(Card card) {

        if(card.getExpDate().isBefore(LocalDate.now())){
            System.out.println("You have entered an incorrect date");
        }else {
            card.setStatus(CardStatus.ACTIVE);
            boolean result=cardRepository.create(card);

            if(result){
            System.out.println("Card has created successfully✅");
            }else {
                System.out.println("Failed❌");
            }
        }
    }

    public List<Card> getList() {

        List<Card> cardList = cardRepository.getAll(CardStatus.ACTIVE);


        return cardList;
    }

    public void list() {
        List<Card> cardList = cardRepository.getAll();
        if(cardList.isEmpty()){
            System.out.println("List of cards is empty🗑️");
        }

        for (Card card : cardList) {
            System.out.println(card);

        }

    }


    public void update(String oldNumber, String newNumber, String newExpDate) {

        List<Card> cardList = cardRepository.getAll(CardStatus.ACTIVE);

        boolean bool = false;
        for (Card card : cardList) {
            if(card.getNumber().equals(oldNumber)){
                card.setNumber(newNumber);
                card.setExpDate(LocalDate.parse(newExpDate));
               bool= cardRepository.update(card,oldNumber);
                break;
            }
        }

        if(bool){
            System.out.println("Card has updated successfully✅");
        }else {
            System.out.println("Card has not found with this number❌");
        }


    }

    public void changeStatus(String number, CardStatus status) {
        List<Card> cardList = cardRepository.getAll();

        for (Card card : cardList) {
            if (card.getNumber().equals(number)) {
                if (status.equals(card.getStatus())) {
                    System.out.println("This card is already "+card.getStatus()+" ❌");
                } else {
                    cardRepository.updateStatus(card, number, status);
                    System.out.println("Card is "+status+"✅");
                }
            }
        }
    }

    public void delete(String number) {
        List<Card> cardList = cardRepository.getAll();
        boolean result=false;
        for (Card card : cardList) {
            if(card.getNumber().equals(number)){
                result= cardRepository.delete(card,number);
               break;
            }
        }
        if(result){
            System.out.println("Card has deleted successfully✅");
        }else {
            System.out.println("Card has not found with this number❌");
        }

    }

    public void addCardForUser(Profile profile,String number) {
        List<Card> cardList = cardRepository.getAll(CardStatus.ACTIVE);

        boolean  bool=false;
        for (Card card : cardList) {
            if(card.getNumber().equals(number)){
//                card.setPhone(profile.getPhone());
               bool= cardRepository.addCardForUser(profile,number);
            }
        }

        if(bool){
            System.out.println("suucess");
        }else {
            System.out.println("error");
        }
    }

    public void listOfUser(Profile profile) {
        List<Card> cardList = cardRepository.getAll();
        for (Card card : cardList) {
            if(profile.getPhone().equals(card.getPhone())){
                System.out.println(card);
            }
        }
    }

    public void changeStatusByUser(String number, CardStatus status) {
        List<Card> cardList = cardRepository.getAll();

        for (Card card : cardList) {
            if (card.getNumber().equals(number)) {
                if (status.equals(card.getStatus())) {
                    System.out.println("This card is already "+card.getStatus()+" ❌");
                } else {
                    cardRepository.updateStatus(card, number, status);
                    System.out.println("Card is "+status+"✅");
                }
            }
        }

    }

    public void fillBalance(Profile profile,String number, double summa) {
        List<Card> cardList = cardRepository.getAll();
        boolean bool=false;
        for (Card card : cardList) {
            if(card.getPhone().equals(profile.getPhone())){
                card.setBalance(card.getBalance()+summa);
              bool=  cardRepository.fillBalance(profile,number,summa);
            }
        }

        if(bool){
            System.out.println("Card balance has filled successfully✅");
        }else {
            System.out.println("Failed❌");
        }

    }
}
