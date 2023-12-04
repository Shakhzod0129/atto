package uz.code.repository;

import uz.code.db.DataBase;
import uz.code.enums.CardStatus;
import uz.code.enums.ProfileStatus;
import uz.code.enums.UserRole;
import uz.code.model.Card;
import uz.code.model.Profile;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<Profile> getAll() {
        List<Profile> dtoList = new LinkedList<>();
        try {
            Connection con = DataBase.getConnection();
            Statement statement = con.createStatement(); // <3>
            String sql = "select * from profile ";
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

    public boolean changeStatus(String phone, ProfileStatus status) {
        try {
            Connection connection = DataBase.getConnection();// <2>
            Statement statement = connection.createStatement(); // <3>
//            String sql = "update student set name = '"+dto.getName()+"', surname ='"+dto.getSurname()+"' where id = "+id;
            String sql = "update profile set status = '%s' where phone = '%s'";
            sql = String.format(sql, status,phone   );
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Profile getProfileByPhone(String phone) {

        Connection connection=DataBase.getConnection();

        try {
            Statement statement = connection.createStatement();

            String sql="select * from profile where phone='"+phone+"'";

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Profile profile=new Profile();
                profile.setName(resultSet.getString("name"));
                profile.setSurname(resultSet.getString("surname"));
                profile.setPhone(resultSet.getString("phone"));
                profile.setPassword(resultSet.getString("password"));
                profile.setRole(UserRole.valueOf(resultSet.getString("role")));
                profile.setStatus(ProfileStatus.valueOf(resultSet.getString("status")));
                return profile;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }

        }
        return null;

    }

    public int saveProfiile(Profile profile) {
        Connection connection = null;
        try {
            connection = DataBase.getConnection();
            String sql = "insert into profile(name,surname,phone,password,role,status,created_date) " +
                    "values ('%s','%s','%s','%s','%s','%s','%s');";
            sql = String.format(sql, profile.getName(), profile.getSurname(), profile.getPhone(), profile.getPassword(),
                    profile.getRole().name(), profile.getStatus().name(), profile.getCreatedDate());
            Statement statement = connection.createStatement();

            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }

        }
        return 0;
    }




}
