package com.practicaticket.dcm.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.practicaticket.dcm.repository.TicketRepository;
import com.practicaticket.dcm.service.TicketQueryService;
import com.practicaticket.dcm.service.TicketService;
import com.practicaticket.dcm.service.criteria.TicketCriteria;
import com.practicaticket.dcm.service.dto.TicketDTO;
import com.practicaticket.dcm.service.dto.TicketUpdateStatusDTO;
import com.practicaticket.dcm.web.rest.errors.BadRequestAlertException;

import jakarta.validation.Valid;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.practicaticket.dcm.domain.Ticket}.
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketResource {

    private static final Logger LOG = LoggerFactory.getLogger(TicketResource.class);

    private static final String ENTITY_NAME = "practicaTicketDcmTicket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketService ticketService;

    private final TicketRepository ticketRepository;

    private final TicketQueryService ticketQueryService;

    public TicketResource(TicketService ticketService, TicketRepository ticketRepository, TicketQueryService ticketQueryService) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
        this.ticketQueryService = ticketQueryService;
    }

    /**
     * {@code POST  /tickets} : Create a new ticket.
     *
     * @param ticketDTO the ticketDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketDTO, or with status {@code 400 (Bad Request)} if the ticket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        LOG.debug("REST request to save Ticket : {}", ticketDTO);
        if (ticketDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ticketDTO = ticketService.save(ticketDTO);
        return ResponseEntity.created(new URI("/api/tickets/" + ticketDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ticketDTO.getId().toString()))
            .body(ticketDTO);
    }
    
    /**
     * {@code POST  /tickets} : Create a new ticket.
     *
     * @param ticketDTO the ticketDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketDTO, or with status {@code 400 (Bad Request)} if the ticket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    /**
     * {@code PUT  /tickets/status} : Update the status of an existing ticket.
     *
     * @param ticketUpdateStatusDTO the DTO containing the ticket ID and new status.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the updated ticketDTO,
     *         or with status {@code 400 (Bad Request)} if the ID is null.
     */
    @PostMapping("/status")
    public ResponseEntity<TicketDTO> updateTicketStatus(@Valid @RequestBody TicketUpdateStatusDTO ticketUpdateStatusDTO) {
        LOG.debug("REST request to update status of Ticket : {}", ticketUpdateStatusDTO);
        
        if (ticketUpdateStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid ticket ID", ENTITY_NAME, "idnull");
        }

        TicketDTO ticketDTO = ticketService.updateStatus(ticketUpdateStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ticketDTO.getId().toString()))
            .body(ticketDTO);
    }


    /**
     * {@code GET  /tickets} : get all the tickets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tickets in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TicketDTO>> getAllTickets(
        TicketCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        Authentication authentication
    ) {
        LOG.debug("REST request to get Tickets by criteria: {}", criteria);

        Page<TicketDTO> page = ticketQueryService.findByCriteria(criteria, pageable, authentication);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tickets/count} : count all the tickets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTickets(TicketCriteria criteria) {
        LOG.debug("REST request to count Tickets by criteria: {}", criteria);
        return ResponseEntity.ok().body(ticketQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tickets/:id} : get the "id" ticket.
     *
     * @param id the id of the ticketDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Ticket : {}", id);
        Optional<TicketDTO> ticketDTO = ticketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketDTO);
    }


}
