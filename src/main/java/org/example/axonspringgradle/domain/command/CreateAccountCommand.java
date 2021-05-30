package org.example.axonspringgradle.domain.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateAccountCommand extends BaseCommand<String> {
    private final double accountBalance;
    private final String currency;

    public CreateAccountCommand(double accountBalance, String currency) {
        super(UUID.randomUUID().toString());
        this.accountBalance = accountBalance;
        this.currency = currency;
    }
}
