package com.edwin.emsp.domain.repository;

import com.edwin.emsp.domain.model.entity.Card;

import java.util.List;

public interface CardRepository {
    Card findById(Integer id);
    List<Card> findByEmail(String email);

    List<Card> findByEmails(List<String> emails);

    Boolean save(Card card);
    Card findCardByUidAndVisibleNumber(String uid, String visibleNumber);

    Integer batchUpdateCards(List<Card> cards);
}