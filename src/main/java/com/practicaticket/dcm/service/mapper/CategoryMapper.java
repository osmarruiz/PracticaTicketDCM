package com.practicaticket.dcm.service.mapper;

import com.practicaticket.dcm.domain.Category;
import com.practicaticket.dcm.service.dto.CategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {}
