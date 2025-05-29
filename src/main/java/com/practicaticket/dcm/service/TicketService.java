package com.practicaticket.dcm.service;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practicaticket.dcm.domain.Ticket;
import com.practicaticket.dcm.domain.enumeration.TicketStatus;
import com.practicaticket.dcm.repository.TicketRepository;
import com.practicaticket.dcm.repository.UserRepository;
import com.practicaticket.dcm.security.SecurityUtils;
import com.practicaticket.dcm.service.dto.TicketDTO;
import com.practicaticket.dcm.service.dto.TicketUpdateStatusDTO;
import com.practicaticket.dcm.service.mapper.TicketMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

/**
 * Service Implementation for managing {@link com.practicaticket.dcm.domain.Ticket}.
 */
@Service
@Transactional
public class TicketService {

    private static final Logger LOG = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository ticketRepository;
    
    private final UserRepository userRepository;


    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
		this.userRepository = userRepository;
        this.ticketMapper = ticketMapper;
    }

    /**
     * Save a ticket.
     *
     * @param ticketDTO the entity to save.
     * @return the persisted entity.
     */
    public TicketDTO save(TicketDTO ticketDTO) {
        LOG.debug("Request to save Ticket : {}", ticketDTO);
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedDate(Instant.now());
        ticket.setUpdatedDate(Instant.now());
        ticket = ticketRepository.save(ticket);
        
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow();
        
        System.out.println(userLogin);
        ticket.setUser(userRepository.findOneByLogin(userLogin).orElseThrow());
        
        return ticketMapper.toDto(ticket);
    }

    /**
     * Update a ticket.
     *
     * @param ticketDTO the entity to save.
     * @return the persisted entity.
     */
    public TicketDTO update(TicketDTO ticketDTO) {
        LOG.debug("Request to update Ticket : {}", ticketDTO);
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        return ticketMapper.toDto(ticket);
    }

    /**
     * Partially update a ticket.
     *
     * @param ticketDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TicketDTO> partialUpdate(TicketDTO ticketDTO) {
        LOG.debug("Request to partially update Ticket : {}", ticketDTO);

        return ticketRepository
            .findById(ticketDTO.getId())
            .map(existingTicket -> {
                ticketMapper.partialUpdate(existingTicket, ticketDTO);

                return existingTicket;
            })
            .map(ticketRepository::save)
            .map(ticketMapper::toDto);
    }

    /**
     * Get all the tickets with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TicketDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ticketRepository.findAllWithEagerRelationships(pageable).map(ticketMapper::toDto);
    }

    /**
     * Get one ticket by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TicketDTO> findOne(Long id) {
        LOG.debug("Request to get Ticket : {}", id);
        return ticketRepository.findOneWithEagerRelationships(id).map(ticketMapper::toDto);
    }

    /**
     * Delete the ticket by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Ticket : {}", id);
        ticketRepository.deleteById(id);
    }

    public TicketDTO updateStatus(@Valid TicketUpdateStatusDTO ticketUpdateStatusDTO) {
        Ticket ticket = ticketRepository.findById(ticketUpdateStatusDTO.getId())
            .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id " + ticketUpdateStatusDTO.getId()));
        
        ticket.setStatus(ticketUpdateStatusDTO.getStatus());
        ticket = ticketRepository.save(ticket);

        return ticketMapper.toDto(ticket);
    }

}
