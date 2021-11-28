package repository;

import connection.UtilConnection;
import entity.Recipient;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RecipientDao {


    public void add(Recipient recipient) throws SQLException {

        String sql = "INSERT INTO RECIPIENTS(ID, EMAIL, NAME, SURNAME, PARTONYMIC) VALUES (?,?,?,?,?)";

        try (Connection connection = UtilConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, recipient.getId());
            preparedStatement.setString(2, recipient.getEmail());
            preparedStatement.setString(3, recipient.getName());
            preparedStatement.setString(4, recipient.getSurname());
            preparedStatement.setString(5, recipient.getPatronymic());

            int res = preparedStatement.executeUpdate();
            log.info(String.valueOf(res));

        }
    }


    public List<Recipient> getAll() throws SQLException {
        List<Recipient> recipientList = new ArrayList<>();

        String sql = "SELECT ID, EMAIL, NAME, SURNAME, PARTONYMIC FROM RECIPIENTS";

        try (Connection connection = UtilConnection.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Recipient recipient = new Recipient();
                recipient.setId(resultSet.getInt("ID"));
                recipient.setEmail(resultSet.getString("EMAIL"));
                recipient.setName(resultSet.getString("NAME"));
                recipient.setSurname(resultSet.getString("SURNAME"));
                recipient.setPatronymic(resultSet.getString("PARTONYMIC"));

                recipientList.add(recipient);

            }
            System.out.println(recipientList);
        }
        return recipientList;
    }


    public Recipient getById(Long id) throws SQLException {

        String sql = "SELECT ID, EMAIL, NAME, SURNAME, PARTONYMIC FROM RECIPIENTS WHERE ID=?";

        Recipient recipient = new Recipient();
        try (Connection connection = UtilConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                recipient.setId(resultSet.getInt("ID"));
                recipient.setEmail(resultSet.getString("EMAIL"));
                recipient.setName(resultSet.getString("NAME"));
                recipient.setSurname(resultSet.getString("SURNAME"));
                recipient.setPatronymic(resultSet.getString("PARTONYMIC"));
                return recipient;
            }

        }
        return null;
    }


    public void update(Recipient recipient) throws SQLException {

        String sql = "UPDATE RECIPIENTS SET NAME=? WHERE ID=?";
        try (Connection connection = UtilConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, recipient.getName());
            preparedStatement.setLong(2, recipient.getId());

            preparedStatement.executeUpdate();
        }

    }


    public void remove(Recipient recipient) throws SQLException {

        String sql = "DELETE FROM RECIPIENTS WHERE ID=?";

        try (Connection connection = UtilConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, recipient.getId());
            preparedStatement.executeUpdate();
        }
        log.info(recipient.getId() + " ID was removed successfully");
    }
}