package cz.kto.pwc.routing.model;

import java.util.List;

public class Route {

    private List<String> route;

    public Route() {
    }

    public Route(String... route) {
        this.route = List.of(route);
    }

    public Route(List<String> route) {
        this.route = route;
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Route{" +
                "route=" + route +
                '}';
    }

}
