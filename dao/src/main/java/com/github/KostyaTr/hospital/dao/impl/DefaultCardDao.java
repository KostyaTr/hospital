package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.CardDao;
import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.model.Card;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;

public class DefaultCardDao implements CardDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultMedicineDao.class);

    private static class SingletonHolder {
        static final CardDao HOLDER_INSTANCE = new DefaultCardDao();
    }

    public static CardDao getInstance() {
        return DefaultCardDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public Card getCardByUserId(Long userId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Card card;
        try {
            card = session.createQuery("select c from Card c where user_id = :user_id", Card.class)
                    .setParameter("user_id", userId).getSingleResult();
        } catch (NoResultException e){
            card = null;
            log.info("No card was found by {} user id", userId, e);
        }
        session.getTransaction().commit();
        session.close();
        return card;
    }

    @Override
    public Long addCard(Card card) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(card);
        session.getTransaction().commit();
        return card.getCardId();
    }

    @Override
    public void updateCardHistory(Long userId, String diagnose) {
        Card card = getInstance().getCardByUserId(userId);
        card.setHistory(card.getHistory() + " " + diagnose);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(card);
        session.getTransaction().commit();
    }
}
