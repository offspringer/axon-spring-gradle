package org.example.axonspringgradle.domain.command;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class BaseCommand<T> {
    @TargetAggregateIdentifier
    public final T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}
