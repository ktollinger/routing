package cz.kto.pwc.routing.service;

import cz.kto.pwc.routing.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CountryDataProviderImpl implements CountryDataProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Cacheable("countries")
    public Map<String, Country> getCountries() {
        logger.warn("HUH: time consuming operation involved");

        final RestTemplate restTemplate = new RestTemplate();
        final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        final ResponseEntity<Country[]> response = restTemplate.getForEntity("https://raw.githubusercontent.com/mledoze/countries/master/countries.json", Country[].class);

        Country[] list = Objects.requireNonNull(response.getBody());

        return Arrays.stream(list).collect(Collectors.toMap(Country::getCca3, c -> c));

    }

}
