package uz.code.service;

import uz.code.controller.Controller;
import uz.code.db.DataBase;
import uz.code.db.InitDataBase;
import uz.code.enums.CardStatus;
import uz.code.model.Card;

import java.time.LocalDate;

public class Demo {
    public static void main(String[] args) {

        InitDataBase.adminInit();
        InitDataBase.addCompanyCard();
        Controller.start();




    }


}
