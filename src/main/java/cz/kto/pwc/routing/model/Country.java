package cz.kto.pwc.routing.model;

import java.util.Objects;
import java.util.Set;

public class Country {

    private String cca3;

    private Set<String> borders;

    public Country() {
    }

    public Country(String cca3, String... borders) {
        this.cca3 = cca3;
        this.borders = Set.of(borders);
    }

    public String getCca3() {
        return cca3;
    }

    public void setCca3(String cca3) {
        this.cca3 = cca3;
    }

    public Set<String> getBorders() {
        return borders;
    }

    public void setBorders(Set<String> borders) {
        this.borders = borders;
    }

    @Override
    public String toString() {
        return "Country{" +
                "cca3='" + cca3 + '\'' +
                ", borders=" + borders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return cca3.equals(country.cca3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cca3);
    }

}
