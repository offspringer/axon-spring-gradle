package org.example.axonspringgradle.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.EntityId;
import org.example.axonspringgradle.domain.command.ChangeCardLimitCommand;
import org.example.axonspringgradle.domain.event.CardLimitChangeDeniedEvent;
import org.example.axonspringgradle.domain.event.CardLimitChangedEvent;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardAggregateMember {
    @EntityId(routingKey = "cardId")
    private String id;

    private String type;

    private double limit;

    private String reason;

    public CardAggregateMember(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @CommandHandler
    protected void on(ChangeCardLimitCommand changeCardLimitCommand) {
        if (changeCardLimitCommand.getLimit() == 0)
            AggregateLifecycle.apply(new CardLimitChangeDeniedEvent(changeCardLimitCommand.getId(), changeCardLimitCommand.getCardId(), "Limit must be greater than zero."));
        else if (changeCardLimitCommand.getLimit() > 10000)
            AggregateLifecycle.apply(new CardLimitChangeDeniedEvent(changeCardLimitCommand.getId(), changeCardLimitCommand.getCardId(), "Limit must be lowe than 10000."));
        else
            AggregateLifecycle.apply(new CardLimitChangedEvent(changeCardLimitCommand.getId(), changeCardLimitCommand.getCardId(), changeCardLimitCommand.getLimit()));
    }

    @EventSourcingHandler
    protected void on(CardLimitChangedEvent cardLimitChangedEvent) {
        this.limit = cardLimitChangedEvent.getLimit();
        this.reason = null;
    }

    @EventSourcingHandler
    protected void on(CardLimitChangeDeniedEvent cardLimitChangeDeniedEvent) {
        this.reason = cardLimitChangeDeniedEvent.getReason();
    }
}
