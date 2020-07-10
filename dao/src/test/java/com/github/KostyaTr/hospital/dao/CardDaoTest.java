package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.model.Card;
import com.github.KostyaTr.hospital.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class CardDaoTest {
    @Autowired
    private CardDao cardDao;

    @Autowired
    private UserDao userDao;

    @Test
    void updateCardHistory(){
        Long userId = userDao.saveUser(new User(null, "cardHistoryUpdate","cardHistoryUpdate","cardHistoryUpdate","cardHistoryUpdate"));
        cardDao.addCard(new Card(null, userId, "history", "address", LocalDate.now(), true));

        cardDao.updateCardHistory(userId, "new history");

        assertEquals(cardDao.getCardByUserId(userId).getHistory(),
                "history new history");
    }

    @Test
    void getCardByUserId(){
        Long userId = userDao.saveUser(new User(null, "cardHistoryGet","cardHistoryGet","cardHistoryGet","cardHistoryGet"));
        cardDao.addCard(new Card(null, userId, "history", "address", LocalDate.now(), true));

        assertNotNull(cardDao.getCardByUserId(userId));
        assertEquals(cardDao.getCardByUserId(userId).getHistory(), "history");
        assertNull(cardDao.getCardByUserId(0L));
    }

    @Test
    void addCard(){
        Long userId = userDao.saveUser(new User(null, "cardHistoryAdd","cardHistoryAdd","cardHistoryAdd","cardHistoryAdd"));

        final Long cardId = cardDao.addCard(new Card(null, userId, "history", "address", LocalDate.now(), true));
        assertNotNull(cardId);
    }
}
