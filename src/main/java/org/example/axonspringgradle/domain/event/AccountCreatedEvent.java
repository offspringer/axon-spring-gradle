package org.example.axonspringgradle.domain.event;

import lombok.Getter;

@Getter
public class AccountCreatedEvent extends BaseEvent<String> {
    private final double accountBalance;
    private final String currency;

    public AccountCreatedEvent(String id, double accountBalance, String currency) {
        super(id);
        this.accountBalance = accountBalance;
        this.currency = currency;
    }
}
