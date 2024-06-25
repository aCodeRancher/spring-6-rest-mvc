package guru.springframework.spring6restmvc.events;

import guru.springframework.spring6restmvc.entities.Beer;

import org.springframework.security.core.Authentication;

/**
 * Created by jt, Spring Framework Guru.
 */

public class BeerCreatedEvent extends BeerEvent{


   public BeerCreatedEvent(Beer beer, Authentication authentication) {
        super(beer, authentication);
    }
}
