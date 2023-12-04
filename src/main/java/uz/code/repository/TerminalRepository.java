package uz.code.repository;

import uz.code.db.DataBase;
import uz.code.enums.TerminalStatus;
import uz.code.model.Terminal;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TerminalRepository {
    public boolean create(Terminal terminal) {
        try {
            Connection con = DataBase.getConnection();
            String sql = "insert into terminal(code,address,status) values(?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql); // <3>

            preparedStatement.setString(1, terminal.getCode());
            preparedStatement.setString(2, terminal.getAddress());
            preparedStatement.setString(3, String.valueOf(terminal.getStatus()));
//            preparedStatement.setTimestamp(4, Timestamp.valueOf(terminal.getCreatedDate()));

            int effectedRows = preparedStatement.executeUpdate(); // <4>
            con.close();
            return effectedRows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Terminal> getAll() {
        List<Terminal> dtoList = new LinkedList<>();
        try {
            Connection con = DataBase.getConnection();
            Statement statement = con.createStatement(); // <3>
            String sql = "select * from terminal";
            ResultSet resultSet = statement.executeQuery(sql); // <4>
            while (resultSet.next()) {
                Terminal dto = new Terminal();
                dto.setCode(resultSet.getString("code"));
                dto.setAddress(resultSet.getString("address"));
                dto.setStatus(TerminalStatus.valueOf(resultSet.getString("status")));
                dto.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());


                dtoList.add(dto);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dtoList;
    }

    public boolean update(Terminal terminal, String oldCode) {
        try {
            Connection connection = DataBase.getConnection();// <2>
            Statement statement = connection.createStatement(); // <3>
//            String sql = "update student set name = '"+dto.getName()+"', surname ='"+dto.getSurname()+"' where id = "+id;
            String sql = "update terminal set address='"+terminal.getAddress()+"' where code='"+oldCode+"'";
//            String sql = "update card set phone = '%s' where number = '%s'";
//            sql = String.format(sql, profile.getPhone(),number);
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean changeStatus(String number, TerminalStatus status) {
        try {
            Connection connection = DataBase.getConnection();// <2>
            Statement statement = connection.createStatement(); // <3>
//            String sql = "update student set name = '"+dto.getName()+"', surname ='"+dto.getSurname()+"' where id = "+id;
            String sql = "update terminal set status = '%s' where code = '%s'";
            sql = String.format(sql, status,number);
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean delete(Terminal terminal, String number) {


        try {
            Connection connection = DataBase.getConnection();// <2>
            Statement statement = connection.createStatement(); // <3>
//            String sql = "update student set name = '"+dto.getName()+"', surname ='"+dto.getSurname()+"' where id = "+id;
            String sql = "update terminal set status = '%s' where code = '%s'";
            sql = String.format(sql, terminal.getStatus(),number);
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

