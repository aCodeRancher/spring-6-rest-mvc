package guru.springframework.spring6restmvc.events;

import guru.springframework.spring6restmvc.entities.Beer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class BeerEvent {
    protected Beer beer = null;
    protected Authentication authentication = null;



}
