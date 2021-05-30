package org.example.axonspringgradle.domain.event;

import lombok.Getter;

@Getter
public class CardAcquiredEvent extends BaseEvent<String> {
    private final String type;
    private final String cardId;

    public CardAcquiredEvent(String id, String type, String cardId) {
        super(id);
        this.type = type;
        this.cardId = cardId;
    }
}
