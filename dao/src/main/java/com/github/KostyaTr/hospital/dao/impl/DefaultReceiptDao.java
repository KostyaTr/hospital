package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.ReceiptDao;
import com.github.KostyaTr.hospital.dao.converter.ReceiptConverter;
import com.github.KostyaTr.hospital.dao.entity.ReceiptEntity;
import com.github.KostyaTr.hospital.model.Receipt;
import org.hibernate.Session;

import javax.persistence.NoResultException;

public class DefaultReceiptDao implements ReceiptDao {
    private static class SingletonHolder{
        static final ReceiptDao HOLDER_INSTANCE = new DefaultReceiptDao();
    }

    public static ReceiptDao getInstance(){
        return DefaultReceiptDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public Receipt getReceiptByUserId(Long userId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        ReceiptEntity receiptEntity;
        try {
            receiptEntity = session.get(ReceiptEntity.class, userId);
        } catch (NoResultException e){
            receiptEntity = null;
        }
        session.getTransaction().commit();
        return ReceiptConverter.fromEntity(receiptEntity);
    }

    @Override
    public boolean insertOrUpdateReceipt(Receipt receipt) {
        ReceiptEntity receiptEntity = ReceiptConverter.toEntity(receipt);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.saveOrUpdate(receiptEntity);
        session.getTransaction().commit();
        return receiptEntity.getUserId() != null;
    }
}
