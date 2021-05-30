package org.example.axonspringgradle.domain.command;

import lombok.Getter;

@Getter
public class CreditMoneyCommand extends BaseCommand<String> {
    private final double creditAmount;
    private final String currency;

    public CreditMoneyCommand(String id, double creditAmount, String currency) {
        super(id);
        this.creditAmount = creditAmount;
        this.currency = currency;
    }
}