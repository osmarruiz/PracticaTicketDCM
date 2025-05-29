package com.practicaticket.dcm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.practicaticket.dcm.domain.enumeration.TicketStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.practicaticket.dcm.domain.Ticket} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

 //   @Lob
    private String description;

//    @NotNull
    private TicketStatus status;

    @Schema(hidden = true)
    //@NotNull
    private Instant createdDate;
    
    @Schema(hidden = true)
    private Instant updatedDate;

    @Schema(implementation = IdOnlyDTO.class)
    private CategoryDTO category;

    @Schema(hidden = true)
    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketDTO)) {
            return false;
        }

        TicketDTO ticketDTO = (TicketDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ticketDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", category=" + getCategory() +
            ", user=" + getUser() +
            "}";
    }
}
