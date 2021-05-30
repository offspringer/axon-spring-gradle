package org.example.axonspringgradle.domain.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AcquireCardCommand extends BaseCommand<String> {
    private final String type;
    private final String cardId;

    public AcquireCardCommand(String id, String type) {
        super(id);
        this.type = type;
        this.cardId = UUID.randomUUID().toString();
    }
}
