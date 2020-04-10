package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.CardDao;
import com.github.KostyaTr.hospital.dao.ChamberDao;
import com.github.KostyaTr.hospital.model.Card;

public class DefaultCardDao implements CardDao {
    private static class SingletonHolder {
        static final CardDao HOLDER_INSTANCE = new DefaultCardDao();
    }

    public static CardDao getInstance() {
        return DefaultCardDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public Card getCardByUserId(Long userId) {
        return null;
    }
}
