package com.practicaticket.dcm.service.mapper;

import com.practicaticket.dcm.domain.Category;
import com.practicaticket.dcm.domain.Ticket;
import com.practicaticket.dcm.domain.User;
import com.practicaticket.dcm.service.dto.CategoryDTO;
import com.practicaticket.dcm.service.dto.TicketDTO;
import com.practicaticket.dcm.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ticket} and its DTO {@link TicketDTO}.
 */
@Mapper(componentModel = "spring")
public interface TicketMapper extends EntityMapper<TicketDTO, Ticket> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryName")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    TicketDTO toDto(Ticket s);

    @Named("categoryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryDTO toDtoCategoryName(Category category);

    @Named("userLogin")
 //   @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
