package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.ReceiptDao;
import com.github.KostyaTr.hospital.dao.converter.ReceiptConverter;
import com.github.KostyaTr.hospital.dao.entity.ReceiptEntity;
import com.github.KostyaTr.hospital.model.Receipt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;

public class DefaultReceiptDao implements ReceiptDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Receipt getReceiptByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        ReceiptEntity receiptEntity;
        try {
            receiptEntity = session.get(ReceiptEntity.class, userId);
        } catch (NoResultException e){
            receiptEntity = null;
        }
        return ReceiptConverter.fromEntity(receiptEntity);
    }

    @Override
    public boolean insertOrUpdateReceipt(Receipt receipt) {
        ReceiptEntity receiptEntity = ReceiptConverter.toEntity(receipt);
        Session session = sessionFactory.getCurrentSession();
        try {
            session.saveOrUpdate(receiptEntity);
        } catch (org.hibernate.NonUniqueObjectException e) {
            session.merge(receiptEntity);
        }
        return receiptEntity.getUserId() != null;
    }
}
