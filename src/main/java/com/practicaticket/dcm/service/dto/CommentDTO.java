package com.practicaticket.dcm.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A DTO for the {@link com.practicaticket.dcm.domain.Comment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentDTO implements Serializable {

    private Long id;

//    @Lob
    private String content;

 //   @NotNull
    private Instant createdDate;

    @Schema(implementation = IdOnlyDTO.class)
    private TicketDTO ticket;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
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
        if (!(o instanceof CommentDTO)) {
            return false;
        }

        CommentDTO commentDTO = (CommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", ticket=" + getTicket() +
            ", user=" + getUser() +
            "}";
    }
}
