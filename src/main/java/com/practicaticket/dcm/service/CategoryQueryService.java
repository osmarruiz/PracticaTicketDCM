package com.practicaticket.dcm.service;

import com.practicaticket.dcm.domain.*; // for static metamodels
import com.practicaticket.dcm.domain.Category;
import com.practicaticket.dcm.repository.CategoryRepository;
import com.practicaticket.dcm.service.criteria.CategoryCriteria;
import com.practicaticket.dcm.service.dto.CategoryDTO;
import com.practicaticket.dcm.service.mapper.CategoryMapper;
import java.util.List;

import jakarta.persistence.metamodel.SingularAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.BooleanFilter;

/**
 * Service for executing complex queries for {@link Category} entities in the database.
 * The main input is a {@link CategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoryQueryService extends QueryService<Category> {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryQueryService.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryQueryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Return a {@link List} of {@link CategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> findByCriteria(CategoryCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<Category> specification = createSpecification(criteria);

        // Log the SQL query and parameters
        List<Category> categories = categoryRepository.findAll(specification);
        for (Category category : categories) {
            LOG.debug("Category entity before mapping: ID={}, Name={}, Enabled={}",
                category.getId(), category.getName(), category.getEnabled());
        }

        return categoryMapper.toDto(categoryRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Category> createSpecification(CategoryCriteria criteria) {
        Specification<Category> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Category_.id),
                buildStringSpecification(criteria.getName(), Category_.name),
                buildStringSpecification(criteria.getDescription(), Category_.description),
                buildBooleanSpecification(criteria.getEnabled(), Category_.enabled)
            );
        }
        return specification;
    }

    /**
     * Helper method to build a Specification for a BooleanFilter.
     * @param filter The BooleanFilter to apply.
     * @param field The SingularAttribute representing the boolean field in the entity.
     * @return A Specification that applies the boolean filter.
     */
    protected Specification<Category> buildBooleanSpecification(BooleanFilter filter, SingularAttribute<Category, Boolean> field) {
        if (filter == null) {
            return null;
        }
        return (root, query, builder) -> {
            if (filter.getEquals() != null) {
                return builder.equal(root.get(field), filter.getEquals());
            }
            if (filter.getSpecified() != null) {
                if (filter.getSpecified()) {
                    return builder.isNotNull(root.get(field));
                } else {
                    return builder.isNull(root.get(field));
                }
            }
            return null;
        };
    }
}
