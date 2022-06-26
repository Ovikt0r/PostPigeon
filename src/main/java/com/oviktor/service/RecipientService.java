package com.oviktor.service;

import com.oviktor.entity.Recipient;
import com.oviktor.repository.RecipientDao;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class RecipientService {

    private final RecipientDao recipientDao = new RecipientDao();

    public void addRecipient(Recipient newRecipient) throws SQLException {
        recipientDao.add(newRecipient);
    }

    public List<Recipient> getAllRecipients() throws SQLException {
        return recipientDao.getAll();
    }

    public Recipient getRecipientById(Long id) throws SQLException {
        return recipientDao.getById(id);
    }
}
