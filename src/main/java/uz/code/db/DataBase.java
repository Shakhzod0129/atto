package uz.code.db;

import uz.code.model.Card;
import uz.code.model.Profile;

import java.sql.*;

public class DataBase {

    public static Connection getConnection(){

        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_jon",
                    "user_jon","12345");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean createProfile(Profile profile) {
        try {
            Connection con = DataBase.getConnection();
            String sql = "insert into profile(phone,name,surname,password,status,role) values(?,?,?,?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql); // <3>

           preparedStatement.setString(1,profile.getPhone());
           preparedStatement.setString(2,profile.getName());
           preparedStatement.setString(3,profile.getSurname());
           preparedStatement.setString(4,profile.getPassword());
           preparedStatement.setString(5, String.valueOf(profile.getStatus()));
           preparedStatement.setString(6, String.valueOf(profile.getRole()));

            int effectedRows = preparedStatement.executeUpdate(); // <4>
            con.close();
            return effectedRows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void createUserTable(){

        Connection connection=DataBase.getConnection();
        try {
            Statement statement = connection.createStatement();

            String sql= """
                    create table if not exists profile(
                    id serial primary key,
                    phone varchar unique,
                    name varchar not null,
                    surname varchar not null,
                    password varchar not null,
                    created_date timestamp default now(),
                    status varchar not null,
                    role varchar not null
                    );""";

            statement.executeUpdate(sql);
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void createCardTable(){

        Connection connection=DataBase.getConnection();
        try {
            Statement statement = connection.createStatement();

            String sql= """
                    create table if not exists card(
                    id serial primary key,
                    number varchar (16) unique,
                    exp_date Date ,
                    balance double precision default(0),
                    status varchar default 'ACTIVE',
                    phone varchar  REFERENCES profile(phone) ,
                    created_date timestamp default now(),
                    visible boolean default true
                    	 
                    );""";

            statement.executeUpdate(sql);
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void createTerminalTable(){

        Connection connection=DataBase.getConnection();
        try {
            Statement statement = connection.createStatement();

            String sql= """
                    create table if not exists terminal(
                    id serial primary key,
                    code varchar  unique,
                    address varchar (30) not null,
                    status varchar default 'ACTIVE',
                    created_date timestamp default now()
                    );""";

            statement.executeUpdate(sql);
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void createTransactionTable(){

        Connection connection=DataBase.getConnection();
        try {
            Statement statement = connection.createStatement();

            String sql= """
                    create table if not exists transaction(
                    id serial  primary key,
                    card_number varchar not null ,
                    amount double precision,
                    type varchar,
                    terminal_code varchar,
                    created_date timestamp default now(),
                    CONSTRAINT fk_card
                    FOREIGN KEY(card_number) REFERENCES card(number)
                    ON UPDATE SET NULL,
                    CONSTRAINT fk_terminal
                    FOREIGN KEY(terminal_code) REFERENCES terminal(code)
                    ON UPDATE SET NULL
                    
                    );""";

            statement.executeUpdate(sql);
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean create(Profile profile, Card card) {
        try {
            Connection con = DataBase.getConnection();
            String sql = "insert into card(number,exp_date,status,phone) values(?,?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql); // <3>

            preparedStatement.setString(1,card.getNumber());
            preparedStatement.setDate(2, Date.valueOf(card.getExpDate()));
            preparedStatement.setString(3, String.valueOf(card.getStatus()));
            preparedStatement.setString(4, profile.getPhone());

            int effectedRows = preparedStatement.executeUpdate(); // <4>
            con.close();
            return effectedRows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
