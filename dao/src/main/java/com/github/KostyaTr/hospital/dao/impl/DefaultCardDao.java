package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.CardDao;
import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.converter.CardConverter;
import com.github.KostyaTr.hospital.dao.entity.CardEntity;
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
        CardEntity cardEntity;
        try {
            cardEntity = session.createQuery("select c from CardEntity c where user_id = :user_id", CardEntity.class)
                    .setParameter("user_id", userId).getSingleResult();
        } catch (NoResultException e){
            cardEntity = null;
            log.info("No card was found by {} user id", userId, e);
        }
        session.getTransaction().commit();
        session.close();
        return CardConverter.fromEntity(cardEntity);
    }

    @Override
    public Long addCard(Card card) {
        CardEntity cardEntity = CardConverter.toEntity(card);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(cardEntity);
        session.getTransaction().commit();
        return cardEntity.getCardId();
    }

    @Override
    public void updateCardHistory(Long userId, String diagnose) {
        Card card = getInstance().getCardByUserId(userId);
        CardEntity cardEntity = CardConverter.toEntity(card);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        cardEntity.setHistory(card.getHistory() + " " + diagnose);
        session.update(cardEntity);
        session.getTransaction().commit();
    }
}
