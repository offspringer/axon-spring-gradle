package org.example.axonspringgradle.domain.event;

import lombok.Getter;

@Getter
public class CardLimitChangedEvent extends BaseEvent<String> {
    private final String cardId;
    private final double limit;

    public CardLimitChangedEvent(String id, String cardId, double limit) {
        super(id);
        this.cardId = cardId;
        this.limit = limit;
    }
}
