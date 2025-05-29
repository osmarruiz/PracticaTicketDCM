package com.practicaticket.dcm.service;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practicaticket.dcm.domain.Comment;
import com.practicaticket.dcm.domain.User;
import com.practicaticket.dcm.repository.CommentRepository;
import com.practicaticket.dcm.security.SecurityUtils;
import com.practicaticket.dcm.service.dto.CommentDTO;
import com.practicaticket.dcm.service.mapper.CommentMapper;

/**
 * Service Implementation for managing {@link com.practicaticket.dcm.domain.Comment}.
 */
@Service
@Transactional
public class CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Save a comment.
     *
     * @param commentDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentDTO save(CommentDTO commentDTO) {
        LOG.debug("Request to save Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setCreatedDate(Instant.now());
        User udto = new User();
        udto.setId(SecurityUtils.getCurrentUserLogin().orElseThrow());
        comment.setUser(udto);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    /**
     * Update a comment.
     *
     * @param commentDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentDTO update(CommentDTO commentDTO) {
        LOG.debug("Request to update Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    /**
     * Partially update a comment.
     *
     * @param commentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommentDTO> partialUpdate(CommentDTO commentDTO) {
        LOG.debug("Request to partially update Comment : {}", commentDTO);

        return commentRepository
            .findById(commentDTO.getId())
            .map(existingComment -> {
                commentMapper.partialUpdate(existingComment, commentDTO);

                return existingComment;
            })
            .map(commentRepository::save)
            .map(commentMapper::toDto);
    }

    /**
     * Get all the comments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CommentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return commentRepository.findAllWithEagerRelationships(pageable).map(commentMapper::toDto);
    }

    /**
     * Get one comment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommentDTO> findOne(Long id) {
        LOG.debug("Request to get Comment : {}", id);
        return commentRepository.findOneWithEagerRelationships(id).map(commentMapper::toDto);
    }

    /**
     * Delete the comment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Comment : {}", id);
        commentRepository.deleteById(id);
    }
}
