package cz.kto.pwc.routing.service;

import cz.kto.pwc.routing.exception.NoSuchCountryException;
import cz.kto.pwc.routing.exception.RouteNotFoundException;
import cz.kto.pwc.routing.exception.RoutingException;
import cz.kto.pwc.routing.model.Country;
import cz.kto.pwc.routing.model.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

@Component
public class RoutingServiceImpl implements RoutingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CountryDataProvider countryDataProvider;

    @Autowired
    public RoutingServiceImpl(final CountryDataProvider countryDataProvider) {
        this.countryDataProvider = countryDataProvider;
    }

    @Override
    public Route route(final String origin, final String destination) throws RoutingException {
        logger.info("loading countries");
        final Map<String, Country> countries = countryDataProvider.getCountries();
        logger.info("countries loaded: {}", countries.size());
        return findRoute(countries, origin, destination);
    }

    /**
     * Look for shortest path in a unoriented unweigted graph using BFS.
     * See https://en.wikipedia.org/wiki/Breadth-first_search
     *
     * @param countries   Set of countries with their respective neighbours.
     * @param origin      Source country.
     * @param destination Target country.
     * @return List of country chain including source and target country.
     */
    private Route findRoute(Map<String, Country> countries, String origin, String destination) throws RoutingException {

        final Country originCountry = countries.get(origin);
        if (originCountry == null) {
            throw new NoSuchCountryException(origin);
        }

        final Country targetCountry = countries.get(destination);
        if (targetCountry == null) {
            throw new NoSuchCountryException(destination);
        }

        if (Objects.equals(originCountry, targetCountry)) {
            return new Route(Collections.singletonList(originCountry.getCca3()));
        }

        // parent map is used in two ways: 1) a replacement for explored set/mark, 2) for backtracking
        final Map<String, String> parentMap = new HashMap<>();
        parentMap.put(originCountry.getCca3(), null);

        final Queue<Country> queue = new LinkedList<>();
        queue.add(originCountry);

        while (!queue.isEmpty()) {
            final Country v = queue.remove();
            for (final String w : v.getBorders()) {
                if (w.equals(destination)) {
                    final List<String> route = new LinkedList<>();
                    route.add(destination);
                    route.add(0, v.getCca3());
                    String parent = parentMap.get(v.getCca3());
                    while (parent != null) {
                        route.add(0, parent);
                        parent = parentMap.get(parent);
                    }
                    return new Route(route);
                }
                if (!parentMap.containsKey(w)) {
                    parentMap.put(w, v.getCca3());
                    Optional.ofNullable(countries.get(w)).ifPresent(queue::add);
                }
            }
        }

        throw new RouteNotFoundException(origin, destination);
    }

}
