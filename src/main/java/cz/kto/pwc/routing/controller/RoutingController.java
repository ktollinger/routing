package cz.kto.pwc.routing.controller;

import cz.kto.pwc.routing.exception.RoutingException;
import cz.kto.pwc.routing.model.Route;
import cz.kto.pwc.routing.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoutingController {

    @Autowired
    private RoutingService routingService;

    @GetMapping(value = "/routing/{origin}/{destination}")
    Route getRoute(@PathVariable String origin, @PathVariable String destination) throws RoutingException {
        return routingService.route(origin, destination);
    }

}
