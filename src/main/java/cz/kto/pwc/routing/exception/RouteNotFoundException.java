package cz.kto.pwc.routing.exception;

public class RouteNotFoundException extends RoutingException {

    public RouteNotFoundException(String origin, String destination) {
        super(String.format("Route not found from country %1$s to the %2$s.", origin, destination));
    }

}
