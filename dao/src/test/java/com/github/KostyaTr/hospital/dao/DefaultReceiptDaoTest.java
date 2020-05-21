package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.entity.ReceiptEntity;
import com.github.KostyaTr.hospital.dao.entity.UserEntity;
import com.github.KostyaTr.hospital.dao.impl.DefaultReceiptDao;
import com.github.KostyaTr.hospital.model.Receipt;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultReceiptDaoTest {
    private ReceiptDao receiptDao = DefaultReceiptDao.getInstance();

    @Test
    void insertOrUpdateReceipt(){
        final UserEntity user = new UserEntity(null, "insertOrUpdateReceipt", "insertOrUpdateReceipt",
                "insertOrUpdateReceipt", "insertOrUpdateReceipt", null, null, null);
        final UserEntity user2 = new UserEntity(null, "insertOrUpdateReceipt2", "insertOrUpdateReceipt2",
                "insertOrUpdateReceipt2", "insertOrUpdateReceipt2", null, null, null);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Long userId = (Long) session.save(user);
        Long userId2 = (Long) session.save(user2);
        ReceiptEntity receipt = new ReceiptEntity(userId, 2d, 2d);
        session.save(receipt);
        session.getTransaction().commit();
        // test for update
        receiptDao.insertOrUpdateReceipt(new Receipt(userId, 4d, 2d));
        assertNotEquals(receipt.getPriceForChamber(), receiptDao.getReceiptByUserId(userId).getPriceForChamber());
        assertEquals(receipt.getPriceForMedicine(), receiptDao.getReceiptByUserId(userId).getPriceForMedicine());
        // test for insert
        assertTrue(receiptDao.insertOrUpdateReceipt(new Receipt(userId2, 3d, 3d)));
    }

    @Test
    void getReceiptByUserId(){
        final UserEntity user = new UserEntity(null, "receiptGetById", "receiptGetById",
                "receiptGetById", "receiptGetById", null, null, null);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Long userId = (Long) session.save(user);
        ReceiptEntity receipt = new ReceiptEntity(userId, 2d, 2d);
        session.save(receipt);
        session.getTransaction().commit();

        assertNotNull(receiptDao.getReceiptByUserId(userId));
        assertEquals(receiptDao.getReceiptByUserId(userId).getPriceForChamber(), 2d);
        assertNull(receiptDao.getReceiptByUserId(0L));
    }
}
