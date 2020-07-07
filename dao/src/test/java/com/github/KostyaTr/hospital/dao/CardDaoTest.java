package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.entity.CardEntity;
import com.github.KostyaTr.hospital.dao.entity.UserEntity;
import com.github.KostyaTr.hospital.dao.impl.DefaultCardDao;
import com.github.KostyaTr.hospital.model.Card;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class CardDaoTest {
    private CardDao cardDao = DefaultCardDao.getInstance();

    @Test
    void updateCardHistory(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        UserEntity user = new UserEntity(null, "cardHistoryUpdate","cardHistoryUpdate","cardHistoryUpdate","cardHistoryUpdate", null, null, null);
        Long userId = (Long) session.save(user);
        CardEntity card = new CardEntity(null, userId, "history", "address", LocalDate.now(), true);
        session.save(card);
        session.getTransaction().commit();

        cardDao.updateCardHistory(userId, "new history");
        assertEquals(cardDao.getCardByUserId(userId).getHistory(),
                "history new history");
    }

    @Test
    void getCardByUserId(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        UserEntity user = new UserEntity(null, "cardHistoryGet","cardHistoryGet","cardHistoryGet","cardHistoryGet", null, null,null);
        Long userId = (Long) session.save(user);
        CardEntity card = new CardEntity(null, userId, "history", "address", LocalDate.now(), true);
        session.save(card);
        session.getTransaction().commit();

        assertNotNull(cardDao.getCardByUserId(userId));
        assertEquals(cardDao.getCardByUserId(userId).getHistory(), "history");
        assertNull(cardDao.getCardByUserId(0L));
    }

    @Test
    void addCard(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        UserEntity user = new UserEntity(null, "cardHistoryAdd","cardHistoryAdd","cardHistoryAdd","cardHistoryAdd", null, null, null);
        Long userId = (Long) session.save(user);
        session.getTransaction().commit();
        final Long cardId = cardDao.addCard(new Card(null, userId, "history", "address", LocalDate.now(), true));
        assertNotNull(cardId);
    }
}
