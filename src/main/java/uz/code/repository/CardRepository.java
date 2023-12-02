package uz.code.repository;

import uz.code.db.DataBase;
import uz.code.enums.CardStatus;
import uz.code.enums.ProfileStatus;
import uz.code.enums.UserRole;
import uz.code.model.Card;
import uz.code.model.Profile;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CardRepository {
    public boolean create(Card card) {
        try {
            Connection con = DataBase.getConnection();
            String sql = "insert into card(number,exp_date,status,phone) values(?,?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql); // <3>

            preparedStatement.setString(1,card.getNumber());
            preparedStatement.setDate(2, Date.valueOf(card.getExpDate()));
//            preparedStatement.setDouble(3,card.getBalance());
            preparedStatement.setString(3, String.valueOf(card.getStatus()));
//            preparedStatement.setTimestamp(5, Timestamp.valueOf(profile.getCreatedDate()));
            preparedStatement.setString(4, card.getPhone());
//            preparedStatement.setTimestamp(5, Timestamp.valueOf(card.getCreatedDate()));




            int effectedRows = preparedStatement.executeUpdate(); // <4>
            con.close();
            return effectedRows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Card> getAll(CardStatus status) {
        List<Card> dtoList = new LinkedList<>();
        try {
            Connection con = DataBase.getConnection();
            Statement statement = con.createStatement(); // <3>
            String sql = "select * from card where status = '" + status.name() + "'";
            ResultSet resultSet = statement.executeQuery(sql); // <4>
            while (resultSet.next()) {
                Card dto = new Card();
                dto.setNumber(resultSet.getString("number"));
                dto.setExpDate(LocalDate.parse(resultSet.getString("exp_date")));
                dto.setBalance(resultSet.getDouble("balance"));
                dto.setStatus(CardStatus.valueOf(resultSet.getString("status")));
                dto.setPhone(resultSet.getString("phone"));
                dto.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());


                dtoList.add(dto);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dtoList;
    }

    public List<Card> getAll() {
        List<Card> dtoList = new LinkedList<>();
        try {
            Connection con = DataBase.getConnection();
            Statement statement = con.createStatement(); // <3>
            String sql = "select * from card";
            ResultSet resultSet = statement.executeQuery(sql); // <4>
            while (resultSet.next()) {
                Card dto = new Card();
                dto.setNumber(resultSet.getString("number"));
                dto.setExpDate(LocalDate.parse(resultSet.getString("exp_date")));
                dto.setBalance(resultSet.getDouble("balance"));
                dto.setStatus(CardStatus.valueOf(resultSet.getString("status")));
                dto.setPhone(resultSet.getString("phone"));
                dto.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());


                dtoList.add(dto);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dtoList;
    }

    public boolean update(Card card,String number) {
        try {
           Connection connection = DataBase.getConnection();// <2>
            Statement statement = connection.createStatement(); // <3>
//            String sql = "update student set name = '"+dto.getName()+"', surname ='"+dto.getSurname()+"' where id = "+id;
            String sql = "update card set number = '%s', exp_date ='%s' where number = '%s'";
            sql = String.format(sql, card.getNumber(), card.getExpDate(),number);
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateStatus(Card card, String number, CardStatus status) {
        try {
            Connection connection = DataBase.getConnection();// <2>
            Statement statement = connection.createStatement(); // <3>
//            String sql = "update student set name = '"+dto.getName()+"', surname ='"+dto.getSurname()+"' where id = "+id;
            String sql = "update card set number = '%s', status ='%s' where number = '%s'";
            sql = String.format(sql, card.getNumber(), status,number);
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Card card, String number) {
        try {
            Connection connection = DataBase.getConnection();// <2>
            Statement statement = connection.createStatement(); // <3>
//            String sql = "update student set name = '"+dto.getName()+"', surname ='"+dto.getSurname()+"' where id = "+id;
            String sql = "update card set number = '%s', status ='BLOCKED' where number = '%s'";
            sql = String.format(sql, card.getNumber(),number);
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean addCardForUser(Profile profile, String number) {
        try {
            Connection connection = DataBase.getConnection();// <2>
            Statement statement = connection.createStatement(); // <3>
//            String sql = "update student set name = '"+dto.getName()+"', surname ='"+dto.getSurname()+"' where id = "+id;
            String sql = "update card set phone = '"+profile.getPhone()+"' where number = '"+number+"'";
//            String sql = "update card set phone = '%s' where number = '%s'";
//            sql = String.format(sql, profile.getPhone(),number);
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean fillBalance(Profile profile, String number, double summa) {
        try {
            Connection connection = DataBase.getConnection();// <2>
            Statement statement = connection.createStatement(); // <3>
//            String sql = "update student set name = '"+dto.getName()+"', surname ='"+dto.getSurname()+"' where id = "+id;
            String sql = "update card set balance=balance +"+summa+" where number = '%s' and phone='%s'";
            sql = String.format(sql,number,profile.getPhone());
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
