package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultReceiptDao;
import com.github.KostyaTr.hospital.model.Receipt;
import com.github.KostyaTr.hospital.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultReceiptDaoTest {
    private ReceiptDao receiptDao = DefaultReceiptDao.getInstance();

    @Test
    void insertOrUpdateReceipt(){
        final User user = new User(null, "insertOrUpdateReceipt", "insertOrUpdateReceipt",
                "insertOrUpdateReceipt", "insertOrUpdateReceipt", null, null, null, null, null, null);
        final User user2 = new User(null, "insertOrUpdateReceipt2", "insertOrUpdateReceipt2",
                "insertOrUpdateReceipt2", "insertOrUpdateReceipt2", null, null, null, null, null, null);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Long userId = (Long) session.save(user);
        Long userId2 = (Long) session.save(user2);
        Receipt receipt = new Receipt(user, 2d, 2d);
        session.save(receipt);
        session.getTransaction().commit();
        // test for update
        receiptDao.insertOrUpdateReceipt(new Receipt(user, 4d, 2d));
        assertNotEquals(receipt.getPriceForChamber(), receiptDao.getReceiptByUserId(userId).getPriceForChamber());
        assertEquals(receipt.getPriceForMedicine(), receiptDao.getReceiptByUserId(userId).getPriceForMedicine());
        // test for insert
        assertTrue(receiptDao.insertOrUpdateReceipt(new Receipt(user2, 3d, 3d)));
    }

    @Test
    void getReceiptByUserId(){
        final User user = new User(null, "receiptGetById", "receiptGetById",
                "receiptGetById", "receiptGetById", null, null, null, null, null, null);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(user);
        Receipt receipt = new Receipt(user, 2d, 2d);
        session.save(receipt);
        session.getTransaction().commit();

        assertNotNull(receiptDao.getReceiptByUserId(user.getUserId()));
        assertEquals(receiptDao.getReceiptByUserId(user.getUserId()).getPriceForChamber(), 2d);
        assertNull(receiptDao.getReceiptByUserId(0L));
    }
}
