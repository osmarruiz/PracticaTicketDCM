package com.practicaticket.dcm.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practicaticket.dcm.repository.CategoryRepository;
import com.practicaticket.dcm.service.CategoryQueryService;
import com.practicaticket.dcm.service.CategoryService;
import com.practicaticket.dcm.service.criteria.CategoryCriteria;
import com.practicaticket.dcm.service.dto.CategoryDTO;
import com.practicaticket.dcm.web.rest.errors.BadRequestAlertException;

import jakarta.validation.Valid;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.practicaticket.dcm.domain.Category}.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryResource.class);

    private static final String ENTITY_NAME = "practicaTicketDcmCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    private final CategoryQueryService categoryQueryService;

    public CategoryResource(
        CategoryService categoryService,
        CategoryRepository categoryRepository,
        CategoryQueryService categoryQueryService
    ) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.categoryQueryService = categoryQueryService;
    }

    /**
     * {@code POST  /categories} : Create a new category.
     *
     * @param categoryDTO the categoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryDTO, or with status {@code 400 (Bad Request)} if the category has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws URISyntaxException {
        LOG.debug("REST request to save Category : {}", categoryDTO);
        if (categoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new category cannot already have an ID", ENTITY_NAME, "idexists");
        }
        categoryDTO = categoryService.save(categoryDTO);
        return ResponseEntity.created(new URI("/api/categories/" + categoryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, categoryDTO.getId().toString()))
            .body(categoryDTO);
    }


    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(CategoryCriteria criteria) {
        LOG.debug("REST request to get Categories by criteria: {}", criteria);

        List<CategoryDTO> entityList = categoryQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /categories/count} : count all the categories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCategories(CategoryCriteria criteria) {
        LOG.debug("REST request to count Categories by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categories/:id} : get the "id" category.
     *
     * @param id the id of the categoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Category : {}", id);
        Optional<CategoryDTO> categoryDTO = categoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryDTO);
    }

}
