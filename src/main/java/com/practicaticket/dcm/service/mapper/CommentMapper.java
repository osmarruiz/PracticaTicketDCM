package com.practicaticket.dcm.service.mapper;

import com.practicaticket.dcm.domain.Comment;
import com.practicaticket.dcm.domain.Ticket;
import com.practicaticket.dcm.domain.User;
import com.practicaticket.dcm.service.dto.CommentDTO;
import com.practicaticket.dcm.service.dto.TicketDTO;
import com.practicaticket.dcm.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "ticket", source = "ticket", qualifiedByName = "ticketTitle")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    CommentDTO toDto(Comment s);

    @Named("ticketTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    TicketDTO toDtoTicketTitle(Ticket ticket);

    @Named("userLogin")
//    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
