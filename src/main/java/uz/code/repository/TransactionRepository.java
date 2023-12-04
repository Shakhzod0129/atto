package uz.code.repository;

import uz.code.db.DataBase;
import uz.code.enums.TransactionType;
import uz.code.model.Card;
import uz.code.model.Terminal;
import uz.code.model.Transaction;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TransactionRepository {
    public boolean fillBalance(Transaction transaction, String number, double summa) {
        try {
            Connection con = DataBase.getConnection();
            String sql = "insert into transaction(card_number,amount,type) values(?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql); // <3>

            preparedStatement.setString(1,number);
            preparedStatement.setDouble(2,summa);
            preparedStatement.setString(3, String.valueOf(transaction.getType()));

//            preparedStatement.setTimestamp(5, Timestamp.valueOf(transaction.getCreatedDate()));

            int effectedRows = preparedStatement.executeUpdate(); // <4>
            con.close();
            return effectedRows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> getAll() {
        List<Transaction> dtoList = new LinkedList<>();
        try {
            Connection con = DataBase.getConnection();
            Statement statement = con.createStatement(); // <3>
            String sql = "select * from transaction";
            ResultSet resultSet = statement.executeQuery(sql); // <4>
            while (resultSet.next()) {
                Transaction dto = new Transaction();
                dto.setCardNumber(resultSet.getString("card_number"));
                dto.setAmount(resultSet.getDouble("amount"));
                dto.setType(TransactionType.valueOf(resultSet.getString("type")));
                dto.setTerminalCode(resultSet.getString("terminal_code"));
                dto.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());


                dtoList.add(dto);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dtoList;
    }

    public boolean payment(Transaction transaction, Card card, Terminal terminal, double summa) {
        try {
            Connection con = DataBase.getConnection();
            String sql = "insert into  transaction (card_number, amount,type,terminal_code) values (?,?,?,?) ";

            PreparedStatement preparedStatement = con.prepareStatement(sql); // <3>
            preparedStatement.setString(1,card.getNumber());
            preparedStatement.setDouble(2,summa);
            preparedStatement.setString(3, String.valueOf(transaction.getType()));
            preparedStatement.setString(4,terminal.getCode());

//            preparedStatement.setTimestamp(5, Timestamp.valueOf(transaction.getCreatedDate()));

            int effectedRows = preparedStatement.executeUpdate(); // <4>
            con.close();
            return effectedRows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
