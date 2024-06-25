package guru.springframework.spring6restmvc.listeners;

import guru.springframework.spring6restmvc.events.*;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.repositories.BeerAuditRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by jt, Spring Framework Guru.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BeerEventListener {

    private final BeerMapper beerMapper;
    private final BeerAuditRepository beerAuditRepository;

    @Async
    @EventListener
    public void listen(BeerEvent event) {

        val beerAudit = beerMapper.beerToBeerAudit(event.getBeer());
        switch (event) {
            case BeerCreatedEvent evt -> beerAudit.setAuditEventType("BEER_CREATED");
            case BeerUpdatedEvent evt ->  beerAudit.setAuditEventType("BEER_UPDATED");
            case BeerPatchedEvent evt ->  beerAudit.setAuditEventType("BEER_PATCHED");
            case BeerDeletedEvent evt ->  beerAudit.setAuditEventType("BEER_DELETED");
            default -> throw new IllegalStateException("Unexpected value: " + event);
        };


        if (event.getAuthentication() != null && event.getAuthentication().getName() != null) {
            beerAudit.setPrincipalName(event.getAuthentication().getName());
        }

        val savedBeerAudit = beerAuditRepository.save(beerAudit);
        log.debug("Beer Audit Saved: " + savedBeerAudit.getId());

    }
}
