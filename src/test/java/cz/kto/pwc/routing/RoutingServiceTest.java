package cz.kto.pwc.routing;

import cz.kto.pwc.routing.exception.NoSuchCountryException;
import cz.kto.pwc.routing.exception.RouteNotFoundException;
import cz.kto.pwc.routing.exception.RoutingException;
import cz.kto.pwc.routing.model.Country;
import cz.kto.pwc.routing.model.Route;
import cz.kto.pwc.routing.service.RoutingService;
import cz.kto.pwc.routing.service.RoutingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoutingServiceTest {

    private RoutingService routingService;

    @BeforeEach
    void setup() {
        final Set<Country> testCountries = new HashSet<>();
        testCountries.add(new Country("AUT", "CZE", "DEU", "HUN", "ITA", "SVK"));
        testCountries.add(new Country("CZE", "AUT", "DEU", "SVK"));
        testCountries.add(new Country("DEU", "AUT", "CZE"));
        testCountries.add(new Country("HUN", "AUT", "SVK"));
        testCountries.add(new Country("ITA", "AUT"));
        testCountries.add(new Country("SVK", "AUT", "CZE", "HUN"));
        testCountries.add(new Country("AUS"));
        this.routingService = new RoutingServiceImpl(() -> testCountries.stream().collect(Collectors.toMap(Country::getCca3, c -> c)));
    }

    @Test
    void route_CZE_CZE() throws RoutingException {
        final Route route = routingService.route("CZE", "CZE");
        Assertions.assertEquals(List.of("CZE"), route.getRoute());
    }

    @Test
    void route_CZE_AUT() throws RoutingException {
        final Route route = routingService.route("CZE", "AUT");
        Assertions.assertEquals(List.of("CZE", "AUT"), route.getRoute());
    }

    @Test
    void route_CZE_ITA() throws RoutingException {
        final Route route = routingService.route("CZE", "ITA");
        Assertions.assertEquals(List.of("CZE", "AUT", "ITA"), route.getRoute());
    }

    @Test
    void route_CZE_AUS() {
        RouteNotFoundException exception = Assertions.assertThrows(
                RouteNotFoundException.class,
                () -> routingService.route("CZE", "AUS")
        );
        Assertions.assertEquals("Route not found from country CZE to the AUS.", exception.getMessage());
    }

    @Test
    void route_CZE_ZOO() {
        NoSuchCountryException exception = Assertions.assertThrows(
                NoSuchCountryException.class,
                () -> routingService.route("CZE", "ZOO")
        );
        Assertions.assertEquals("Country ZOO does not exist.", exception.getMessage());
    }

    @Test
    void route_CZE_null() {
        NoSuchCountryException exception = Assertions.assertThrows(
                NoSuchCountryException.class,
                () -> routingService.route("CZE", null)
        );
        Assertions.assertEquals("Country null does not exist.", exception.getMessage());
    }

    @Test
    void route_null_CZE() {
        NoSuchCountryException exception = Assertions.assertThrows(
                NoSuchCountryException.class,
                () -> routingService.route(null, "CZE")
        );
        Assertions.assertEquals("Country null does not exist.", exception.getMessage());
    }

}
