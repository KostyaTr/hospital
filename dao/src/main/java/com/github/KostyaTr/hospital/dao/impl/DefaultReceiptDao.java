package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.ReceiptDao;
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
        Receipt receipt;
        try {
            receipt = session.get(Receipt.class, userId);
        } catch (NoResultException e){
            receipt = null;
        }
        session.getTransaction().commit();
        return receipt;
    }

    @Override
    public boolean insertOrUpdateReceipt(Receipt receipt) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.saveOrUpdate(receipt);
        session.getTransaction().commit();
        return receipt.getUser() != null;
    }

    /*@Override
    public boolean insertReceipt(Receipt receipt) {
        final String sql = "insert into receipt values(?, ?, ?) ;";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, receipt.getUserId());
            preparedStatement.setDouble(2, receipt.getPriceForChamber());
            preparedStatement.setDouble(3, receipt.getPriceForMedicine());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
}
