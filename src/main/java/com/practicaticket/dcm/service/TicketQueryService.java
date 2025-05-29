package com.practicaticket.dcm.service;

import com.practicaticket.dcm.domain.*; // for static metamodels
import com.practicaticket.dcm.domain.Ticket;
import com.practicaticket.dcm.repository.TicketRepository;
import com.practicaticket.dcm.security.AuthoritiesConstants;
import com.practicaticket.dcm.security.SecurityUtils;
import com.practicaticket.dcm.service.criteria.TicketCriteria;
import com.practicaticket.dcm.service.dto.TicketDTO;
import com.practicaticket.dcm.service.mapper.TicketMapper;
import jakarta.persistence.criteria.JoinType;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Ticket} entities in the database.
 * The main input is a {@link TicketCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TicketDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TicketQueryService extends QueryService<Ticket> {

    private static final Logger LOG = LoggerFactory.getLogger(TicketQueryService.class);

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    public TicketQueryService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    /**
     * Return a {@link Page} of {@link TicketDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TicketDTO> findByCriteria(TicketCriteria criteria, Pageable page, Authentication authentication) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ticket> specification = createSpecification(criteria);
        Page<TicketDTO> dto = ticketRepository.findAll(specification, page).map(ticketMapper::toDto);
        
        if(SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN))
        	return dto;
        
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow();
        
        List<TicketDTO> dtol = dto.filter(ticket -> ticket.getUser().getLogin().equals(login)).toList();
        
        return new PageImpl<TicketDTO>(dtol, dto.getPageable(), dtol.size());
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TicketCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Ticket> specification = createSpecification(criteria);
        return ticketRepository.count(specification);
    }

    /**
     * Function to convert {@link TicketCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ticket> createSpecification(TicketCriteria criteria) {
        Specification<Ticket> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Ticket_.id),
                buildStringSpecification(criteria.getTitle(), Ticket_.title),
                buildSpecification(criteria.getStatus(), Ticket_.status),
                buildRangeSpecification(criteria.getCreatedDate(), Ticket_.createdDate),
                buildRangeSpecification(criteria.getUpdatedDate(), Ticket_.updatedDate),
                buildSpecification(criteria.getCategoryId(), root -> root.join(Ticket_.category, JoinType.LEFT).get(Category_.id)),
                buildSpecification(criteria.getUserId(), root -> root.join(Ticket_.user, JoinType.LEFT).get(User_.id))
            );
        }
        return specification;
    }
}
