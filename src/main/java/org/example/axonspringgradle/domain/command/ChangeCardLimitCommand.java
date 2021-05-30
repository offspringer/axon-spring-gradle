package org.example.axonspringgradle.domain.command;

import lombok.Getter;

@Getter
public class ChangeCardLimitCommand extends BaseCommand<String> {
    private final String cardId;
    private final double limit;

    public ChangeCardLimitCommand(String id, String cardId, double limit) {
        super(id);
        this.cardId = cardId;
        this.limit = limit;
    }
}
