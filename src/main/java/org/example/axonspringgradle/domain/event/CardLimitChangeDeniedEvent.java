package org.example.axonspringgradle.domain.event;

import lombok.Getter;

@Getter
public class CardLimitChangeDeniedEvent extends BaseEvent<String> {
    private final String cardId;
    private final String reason;

    public CardLimitChangeDeniedEvent(String id, String cardId, String reason) {
        super(id);
        this.cardId = cardId;
        this.reason = reason;
    }
}
