package com.practicaticket.dcm.service.dto;

import com.practicaticket.dcm.domain.enumeration.TicketStatus;

import jakarta.validation.constraints.NotNull;

public class TicketUpdateStatusDTO {
	
    @NotNull
    private Long id;
    @NotNull
    private TicketStatus status;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TicketStatus getStatus() {
		return status;
	}
	public void setStatus(TicketStatus status) {
		this.status = status;
	}
    
    
}
