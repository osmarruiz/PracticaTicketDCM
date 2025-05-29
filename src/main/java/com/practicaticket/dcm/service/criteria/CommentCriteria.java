package com.practicaticket.dcm.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.practicaticket.dcm.domain.Comment} entity. This class is used
 * in {@link com.practicaticket.dcm.web.rest.CommentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdDate;

    private LongFilter ticketId;

    private StringFilter userId;

    private Boolean distinct;

    public CommentCriteria() {}

    public CommentCriteria(CommentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.ticketId = other.optionalTicketId().map(LongFilter::copy).orElse(null);
        this.userId = other.optionalUserId().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CommentCriteria copy() {
        return new CommentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public Optional<InstantFilter> optionalCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            setCreatedDate(new InstantFilter());
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LongFilter getTicketId() {
        return ticketId;
    }

    public Optional<LongFilter> optionalTicketId() {
        return Optional.ofNullable(ticketId);
    }

    public LongFilter ticketId() {
        if (ticketId == null) {
            setTicketId(new LongFilter());
        }
        return ticketId;
    }

    public void setTicketId(LongFilter ticketId) {
        this.ticketId = ticketId;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public Optional<StringFilter> optionalUserId() {
        return Optional.ofNullable(userId);
    }

    public StringFilter userId() {
        if (userId == null) {
            setUserId(new StringFilter());
        }
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommentCriteria that = (CommentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(ticketId, that.ticketId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, ticketId, userId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalTicketId().map(f -> "ticketId=" + f + ", ").orElse("") +
            optionalUserId().map(f -> "userId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
