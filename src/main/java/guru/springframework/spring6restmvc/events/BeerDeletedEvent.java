package guru.springframework.spring6restmvc.events;


import guru.springframework.spring6restmvc.entities.Beer;
import org.springframework.security.core.Authentication;

public class BeerDeletedEvent extends BeerEvent {


   public BeerDeletedEvent(Beer beer, Authentication authentication) {
        super(beer, authentication);
    }
}
