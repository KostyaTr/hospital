package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.dao.entity.ReceiptEntity;
import com.github.KostyaTr.hospital.dao.entity.UserEntity;
import com.github.KostyaTr.hospital.model.Receipt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultReceiptDaoTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ReceiptDao receiptDao;

    @Test
    void insertOrUpdateReceipt(){
        Session session = sessionFactory.getCurrentSession();
        final UserEntity user = new UserEntity(null, "insertOrUpdateReceipt", "insertOrUpdateReceipt",
                "insertOrUpdateReceipt", "insertOrUpdateReceipt", null, null, null);
        final UserEntity user2 = new UserEntity(null, "insertOrUpdateReceipt2", "insertOrUpdateReceipt2",
                "insertOrUpdateReceipt2", "insertOrUpdateReceipt2", null, null, null);
        Long userId = (Long) session.save(user);
        Long userId2 = (Long) session.save(user2);
        ReceiptEntity receipt = new ReceiptEntity(userId, 2d, 2d);
        session.save(receipt);
        // test for update
        receiptDao.insertOrUpdateReceipt(new Receipt(userId, 4d, 2d));
        assertNotEquals(2d, receiptDao.getReceiptByUserId(userId).getPriceForChamber());
        assertEquals(receipt.getPriceForMedicine(), receiptDao.getReceiptByUserId(userId).getPriceForMedicine());
        // test for insert
        assertTrue(receiptDao.insertOrUpdateReceipt(new Receipt(userId2, 3d, 3d)));
    }

    @Test
    void getReceiptByUserId(){
        Session session = sessionFactory.getCurrentSession();
        final UserEntity user = new UserEntity(null, "receiptGetById", "receiptGetById",
                "receiptGetById", "receiptGetById", null, null, null);
        Long userId = (Long) session.save(user);
        ReceiptEntity receipt = new ReceiptEntity(userId, 2d, 2d);
        session.save(receipt);

        assertNotNull(receiptDao.getReceiptByUserId(userId));
        assertEquals(receiptDao.getReceiptByUserId(userId).getPriceForChamber(), 2d);
        assertNull(receiptDao.getReceiptByUserId(0L));
    }
}
