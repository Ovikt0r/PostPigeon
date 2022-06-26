package com.oviktor.repository;

import com.oviktor.connection.DbConnection;
import com.oviktor.entity.Recipient;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RecipientDao {


    public void add(Recipient recipient) throws SQLException {

        String sql = "INSERT INTO RECIPIENTS(ID, EMAIL, NAME, SURNAME, PARTONYMIC) VALUES (?,?,?,?,?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, recipient.getId());
            preparedStatement.setString(2, recipient.getEmail());
            preparedStatement.setString(3, recipient.getName());
            preparedStatement.setString(4, recipient.getSurname());
            preparedStatement.setString(5, recipient.getPatronymic());

            preparedStatement.executeUpdate();
        }
    }

    public List<Recipient> getAll() throws SQLException {
        List<Recipient> recipients = new ArrayList<>();

        String sql = "SELECT ID, EMAIL, NAME, SURNAME, PARTONYMIC FROM RECIPIENTS";

        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {


            while (resultSet.next()) {
                recipients.add(
                        Recipient.builder()
                                .id(resultSet.getLong("ID"))
                                .email(resultSet.getString("EMAIL"))
                                .name(resultSet.getString("NAME"))
                                .surname(resultSet.getString("SURNAME"))
                                .patronymic(resultSet.getString("PARTONYMIC"))
                                .build()
                );
            }
        }
        return recipients;
    }


    public Recipient getById(Long id) throws SQLException {

        String sql = "SELECT ID, EMAIL, NAME, SURNAME, PARTONYMIC FROM RECIPIENTS WHERE ID=?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Recipient.builder()
                            .id(resultSet.getLong("ID"))
                            .email(resultSet.getString("EMAIL"))
                            .name(resultSet.getString("NAME"))
                            .surname(resultSet.getString("SURNAME"))
                            .patronymic(resultSet.getString("PARTONYMIC"))
                            .build();
                }
            }

        }
        return null;
    }

    public void update(Recipient recipient) throws SQLException {

        String sql = "UPDATE RECIPIENTS SET NAME=? WHERE ID=?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, recipient.getName());
            preparedStatement.setLong(2, recipient.getId());

            preparedStatement.executeUpdate();
        }

    }

    public void removeById(Long id) throws SQLException {

        String sql = "DELETE FROM RECIPIENTS WHERE ID=?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
        log.info(id + "th ID was removed successfully");
    }
}