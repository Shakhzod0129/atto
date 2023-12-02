package uz.code.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    public static Connection getConnection(){

        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_jon",
                    "user_jon","12345");

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
                    phone varchar primary key,
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
                    number varchar (16) primary key,
                    exp_date Date ,
                    balance double precision default(0),
                    status varchar default 'ACTIVE',
                    phone varchar  REFERENCES profile(phone) ,
                    created_date timestamp default now()
                    	 
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
                    code varchar  primary key,
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
                    create table if not exists card(
                    id serial  primary key,
                    card_number varchar not null ,
                    amount double precision,
                    terminal_code varchar,
                    created_date timestamp default now(),
                    FOREIGN KEY(card_number) REFERENCES card(number),
                    FOREIGN KEY(terminal_code) REFERENCES terminal(code)
                    );""";

            statement.executeUpdate(sql);
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
