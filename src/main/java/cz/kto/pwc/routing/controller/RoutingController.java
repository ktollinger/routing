package cz.kto.pwc.routing.controller;

import cz.kto.pwc.routing.model.Route;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoutingController {

    @GetMapping(value = "/routing/{origin}/{destination}")
    Route getRoute(@PathVariable String origin, @PathVariable String destination) {
        final Route result = new Route();
        result.setRoute(List.of(origin, destination));
        return result;
    }

}
