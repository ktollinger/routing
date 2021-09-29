package cz.kto.pwc.routing.exception;

public class NoSuchCountryException extends RoutingException {

    public NoSuchCountryException(String country) {
        super(String.format("Country %1$s does not exist.", country));
    }

}
