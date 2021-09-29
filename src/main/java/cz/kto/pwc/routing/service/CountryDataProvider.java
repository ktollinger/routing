package cz.kto.pwc.routing.service;

import cz.kto.pwc.routing.model.Country;

import java.util.Map;

public interface CountryDataProvider {

    Map<String, Country> getCountries();

}
