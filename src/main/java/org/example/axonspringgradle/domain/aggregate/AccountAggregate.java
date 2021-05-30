package org.example.axonspringgradle.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.axonspringgradle.domain.command.*;
import org.example.axonspringgradle.domain.enumeration.Status;
import org.example.axonspringgradle.domain.event.*;

import java.util.ArrayList;
import java.util.List;

@Aggregate
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountAggregate {
    @AggregateIdentifier
    private String id;

    private double accountBalance;

    private String currency;

    private Status status;

    @AggregateMember
    private List<CardAggregateMember> cards = new ArrayList<>();

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        AggregateLifecycle.apply(new AccountCreatedEvent(createAccountCommand.id, createAccountCommand.getAccountBalance(), createAccountCommand.getCurrency()));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent accountCreatedEvent) {
        this.id = accountCreatedEvent.id;
        this.accountBalance = accountCreatedEvent.getAccountBalance();
        this.currency = accountCreatedEvent.getCurrency();
        this.status = Status.CREATED;

        AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
    }

    @EventSourcingHandler
    protected void on(AccountActivatedEvent accountActivatedEvent) {
        this.status = accountActivatedEvent.getStatus();
    }

    @CommandHandler
    protected void on(CreditMoneyCommand creditMoneyCommand) {
        AggregateLifecycle.apply(new MoneyCreditedEvent(creditMoneyCommand.id, creditMoneyCommand.getCreditAmount(), creditMoneyCommand.getCurrency()));
    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvent moneyCreditedEvent) {
        if (this.accountBalance < 0 & (this.accountBalance + moneyCreditedEvent.getCreditAmount()) >= 0) {
            AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
        }

        this.accountBalance += moneyCreditedEvent.getCreditAmount();
    }

    @CommandHandler
    protected void on(DebitMoneyCommand debitMoneyCommand) {
        AggregateLifecycle.apply(new MoneyDebitedEvent(debitMoneyCommand.id, debitMoneyCommand.getDebitAmount(), debitMoneyCommand.getCurrency()));
    }

    @EventSourcingHandler
    protected void on(MoneyDebitedEvent moneyDebitedEvent) {
        if (this.accountBalance >= 0 & (this.accountBalance - moneyDebitedEvent.getDebitAmount()) < 0) {
            AggregateLifecycle.apply(new AccountHeldEvent(this.id, Status.HOLD));
        }

        this.accountBalance -= moneyDebitedEvent.getDebitAmount();
    }

    @EventSourcingHandler
    protected void on(AccountHeldEvent accountHeldEvent) {
        this.status = accountHeldEvent.getStatus();
    }

    @CommandHandler
    protected void on(AcquireCardCommand acquireCardCommand) {
        AggregateLifecycle.apply(new CardAcquiredEvent(acquireCardCommand.getId(), acquireCardCommand.getType(), acquireCardCommand.getCardId()));
    }

    @EventSourcingHandler
    protected void on(CardAcquiredEvent cardAcquiredEvent) {
        this.cards.add(new CardAggregateMember(cardAcquiredEvent.getCardId(), cardAcquiredEvent.getType()));
    }

    @CommandHandler
    protected void on(CancelCardCommand cancelCardCommand) {
        AggregateLifecycle.apply(new CardCanceledEvent(cancelCardCommand.getId(), cancelCardCommand.getCardId()));
    }

    @EventSourcingHandler
    protected void on(CardCanceledEvent cardCanceledEvent) {
        this.cards.removeIf(i -> i.getId().equals(cardCanceledEvent.getCardId()));
    }
}
