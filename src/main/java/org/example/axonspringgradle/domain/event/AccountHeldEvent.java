package org.example.axonspringgradle.domain.event;

import lombok.Getter;
import org.example.axonspringgradle.domain.enumeration.Status;

@Getter
public class AccountHeldEvent extends BaseEvent<String> {
    private final Status status;

    public AccountHeldEvent(String id, Status status) {
        super(id);
        this.status = status;
    }
}
