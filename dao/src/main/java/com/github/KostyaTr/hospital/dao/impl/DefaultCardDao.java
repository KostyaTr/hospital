package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.CardDao;
import com.github.KostyaTr.hospital.dao.converter.CardConverter;
import com.github.KostyaTr.hospital.dao.entity.CardEntity;
import com.github.KostyaTr.hospital.model.Card;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;

public class DefaultCardDao implements CardDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultMedicineDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Card getCardByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        CardEntity cardEntity;
        try {
            cardEntity = session.createQuery("select c from CardEntity c where user_id = :user_id", CardEntity.class)
                    .setParameter("user_id", userId).getSingleResult();
        } catch (NoResultException e){
            cardEntity = null;
            log.info("No card was found by {} user id", userId, e);
        }
        return CardConverter.fromEntity(cardEntity);
    }

    @Override
    public Long addCard(Card card) {
        CardEntity cardEntity = CardConverter.toEntity(card);
        Session session = sessionFactory.getCurrentSession();
        session.save(cardEntity);
        return cardEntity.getCardId();
    }

    @Override
    public void updateCardHistory(Long userId, String history) {
        Card card = getCardByUserId(userId);
        CardEntity cardEntity = CardConverter.toEntity(card);
        Session session = sessionFactory.getCurrentSession();
        cardEntity.setHistory(card.getHistory() + " " + history);
        session.merge(cardEntity);
    }
}
