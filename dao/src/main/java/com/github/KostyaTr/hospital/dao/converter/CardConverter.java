package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.CardEntity;
import com.github.KostyaTr.hospital.model.Card;

public class CardConverter {
    public static Card fromEntity(CardEntity cardEntity) {
        if (cardEntity == null) {
            return null;
        }
        return new Card(
                cardEntity.getCardId(),
                cardEntity.getUserId(),
                cardEntity.getHistory(),
                cardEntity.getAddress(),
                cardEntity.getBirthday(),
                cardEntity.isInsurance()
        );
    }

    public static CardEntity toEntity(Card card) {
        if (card == null) {
            return null;
        }
        final CardEntity cardEntity = new CardEntity();
        cardEntity.setCardId(card.getCardId());
        cardEntity.setUserId(card.getUserId());
        cardEntity.setHistory(card.getHistory());
        cardEntity.setAddress(card.getAddress());
        cardEntity.setBirthday(card.getBirthday());
        cardEntity.setInsurance(card.isInsurance());
        return cardEntity;
    }
}
