package org.example.axonspringgradle.domain.event;

import lombok.Getter;

@Getter
public class CardCanceledEvent extends BaseEvent<String> {
    private final String cardId;

    public CardCanceledEvent(String id, String cardId) {
        super(id);
        this.cardId = cardId;
    }
}