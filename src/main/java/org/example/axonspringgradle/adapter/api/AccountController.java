package org.example.axonspringgradle.adapter.api;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.axonspringgradle.domain.command.AcquireCardCommand;
import org.example.axonspringgradle.domain.command.CancelCardCommand;
import org.example.axonspringgradle.domain.command.ChangeCardLimitCommand;
import org.example.axonspringgradle.domain.command.CreateAccountCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private CommandGateway commandGateway;

    /**
     * http://localhost:8080/account/77b3063d-cc3f-4c1d-a921-4d2b9509fa44
     */
    @GetMapping("/{accountId}")
    public void get(@PathVariable String accountId) {

    }

    /**
     * http://localhost:8080/account/create
     * http://localhost:8080/account/create?balance=0&currency=BRL
     */
    @PostMapping("/create")
    public void create(@RequestParam(defaultValue = "0") double balance, @RequestParam(defaultValue = "BRL") String currency) {
        this.commandGateway.send(new CreateAccountCommand(balance, currency));
    }

    /**
     * http://localhost:8080/account/77b3063d-cc3f-4c1d-a921-4d2b9509fa44/add-card
     * http://localhost:8080/account/77b3063d-cc3f-4c1d-a921-4d2b9509fa44/add-card?type=VISA
     */
    @PostMapping(value = "/{accountId}/add-card")
    public void acquireCard(@PathVariable String accountId, @RequestParam(defaultValue = "VISA") String type) {
        this.commandGateway.send(new AcquireCardCommand(accountId, type));
    }

    /**
     * http://localhost:8080/account/77b3063d-cc3f-4c1d-a921-4d2b9509fa44/card/f439fee6-34b9-46c4-a61a-33b7f737a9ce?action=cancel
     */
    @DeleteMapping(value = "/{accountId}/card/{cardId}", params = {"action=cancel"})
    public void cancelCard(@PathVariable String accountId, @PathVariable String cardId) {
        this.commandGateway.send(new CancelCardCommand(accountId, cardId));
    }

    /**
     * http://localhost:8080/account/77b3063d-cc3f-4c1d-a921-4d2b9509fa44/card/f439fee6-34b9-46c4-a61a-33b7f737a9ce?action=change-limit&limit=1000
     */
    @PatchMapping(value = "/{accountId}/card/{cardId}", params = {"action=change-limit", "limit"})
    public void changeCardLimit(@PathVariable String accountId, @PathVariable String cardId, @RequestParam double limit) {
        this.commandGateway.send(new ChangeCardLimitCommand(accountId, cardId, limit));
    }
}
