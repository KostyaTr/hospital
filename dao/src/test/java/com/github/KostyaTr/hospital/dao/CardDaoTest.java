package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultCardDao;
import com.github.KostyaTr.hospital.model.Card;
import com.github.KostyaTr.hospital.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class CardDaoTest {
    private CardDao cardDao = DefaultCardDao.getInstance();

    @Test
    void updateCardHistory(){
        User user = new User(null, "cardHistoryUpdate","cardHistoryUpdate","cardHistoryUpdate","cardHistoryUpdate", null, null, null, null, null, null);
        Card card = new Card(null, null, "history", "address", new Date(), true);
        user.setCard(card);
        card.setUser(user);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

        cardDao.updateCardHistory(user.getUserId(), "new history");
        assertEquals(cardDao.getCardByUserId(user.getUserId()).getHistory(),
                "history new history");
    }

    @Test
    void getCardByUserId(){
        User user = new User(null, "cardHistoryGet","cardHistoryGet","cardHistoryGet","cardHistoryGet", null, null, null, null, null, null);
        Card card = new Card(null, null, "history", "address", new Date(), true);
        user.setCard(card);
        card.setUser(user);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

        assertNotNull(cardDao.getCardByUserId(user.getUserId()));
        assertEquals(cardDao.getCardByUserId(user.getUserId()).getHistory(), "history");
        assertNull(cardDao.getCardByUserId(0L));
    }

    @Test
    void addCard(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        User user = new User(null, "cardHistoryAdd","cardHistoryAdd","cardHistoryAdd","cardHistoryAdd", null, null, null, null, null, null);
        session.save(user);
        session.getTransaction().commit();
        final Long cardId = cardDao.addCard(new Card(null, user, "history", "address", new Date(), true));
        assertNotNull(cardId);
    }
}
