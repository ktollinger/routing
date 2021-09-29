package cz.kto.pwc.routing.service;

import cz.kto.pwc.routing.exception.RoutingException;
import cz.kto.pwc.routing.model.Route;

public interface RoutingService {

    Route route(String origin, String destination) throws RoutingException;

}
