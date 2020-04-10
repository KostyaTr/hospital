package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Card;

public interface CardDao {
    Card getCardByUserId(Long userId);

    Long addCard(Card card);
}
