package uz.code.repository;

import uz.code.db.DataBase;
import uz.code.enums.ProfileStatus;
import uz.code.enums.UserRole;
import uz.code.model.Profile;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProfileRepository {


    public boolean create(Profile profile) {
        try {
            Connection con = DataBase.getConnection();
            String sql = "insert into profile(name,surname,phone,password,status,role) values(?,?,?,?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql); // <3>

            preparedStatement.setString(1,profile.getName());
            preparedStatement.setString(2,profile.getSurname());
            preparedStatement.setString(3,profile.getPhone());
            preparedStatement.setString(4,profile.getPassword());
//            preparedStatement.setTimestamp(5, Timestamp.valueOf(profile.getCreatedDate()));
            preparedStatement.setString(5, String.valueOf(profile.getStatus()));
            preparedStatement.setString(6, String.valueOf(profile.getRole()));




            int effectedRows = preparedStatement.executeUpdate(); // <4>
            con.close();
            return effectedRows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Profile> getAll(ProfileStatus status) {
        List<Profile> dtoList = new LinkedList<>();
        try {
            Connection con = DataBase.getConnection();
            Statement statement = con.createStatement(); // <3>
            String sql = "select * from profile where status = '" + status.name() + "'";
            ResultSet resultSet = statement.executeQuery(sql); // <4>
            while (resultSet.next()) {
                Profile dto = new Profile();
                dto.setName(resultSet.getString("name"));
                dto.setSurname(resultSet.getString("surname"));
                dto.setPhone(resultSet.getString("phone"));
                dto.setPassword(resultSet.getString("password"));
                dto.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                dto.setStatus(ProfileStatus.valueOf(resultSet.getString("status")));
                dto.setRole(UserRole.valueOf(resultSet.getString("role")));

                dtoList.add(dto);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dtoList;
    }
}
