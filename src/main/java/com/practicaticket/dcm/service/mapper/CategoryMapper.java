package com.practicaticket.dcm.service.mapper;

import com.practicaticket.dcm.domain.Category;
import com.practicaticket.dcm.service.dto.CategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Override
    @Mapping(source = "enabled", target = "enabled") // Asegúrate de que este mapeo explícito esté aquí
    CategoryDTO toDto(Category entity);

    // Agrega esta anotación al método toEntity de la interfaz
    @Override
    @Mapping(source = "enabled", target = "enabled") // Y aquí para mapear de DTO a entidad
    Category toEntity(CategoryDTO dto);

    // Y para partialUpdate si lo usas y necesitas que 'enabled' se actualice
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "enabled", target = "enabled") // También para partialUpdate
    void partialUpdate(@MappingTarget Category entity, CategoryDTO dto);
}
