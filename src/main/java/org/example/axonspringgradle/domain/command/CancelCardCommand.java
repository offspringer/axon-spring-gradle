package org.example.axonspringgradle.domain.command;

import lombok.Getter;

@Getter
public class CancelCardCommand extends BaseCommand<String> {
    private final String cardId;

    public CancelCardCommand(String id, String cardId) {
        super(id);
        this.cardId = cardId;
    }
}
