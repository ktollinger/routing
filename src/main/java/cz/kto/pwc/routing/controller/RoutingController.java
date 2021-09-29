package cz.kto.pwc.routing.controller;

import cz.kto.pwc.routing.exception.RoutingException;
import cz.kto.pwc.routing.model.Route;
import cz.kto.pwc.routing.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RoutingController {

    private final RoutingService routingService;

    @Autowired
    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping(value = "/routing/{origin}/{destination}")
    Route getRoute(@PathVariable String origin, @PathVariable String destination) {
        try {
            return routingService.route(origin, destination);
        } catch (RoutingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
