package com.practicaticket.dcm.domain;

import static com.practicaticket.dcm.domain.CommentTestSamples.*;
import static com.practicaticket.dcm.domain.TicketTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.practicaticket.dcm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comment.class);
        Comment comment1 = getCommentSample1();
        Comment comment2 = new Comment();
        assertThat(comment1).isNotEqualTo(comment2);

        comment2.setId(comment1.getId());
        assertThat(comment1).isEqualTo(comment2);

        comment2 = getCommentSample2();
        assertThat(comment1).isNotEqualTo(comment2);
    }

    @Test
    void ticketTest() {
        Comment comment = getCommentRandomSampleGenerator();
        Ticket ticketBack = getTicketRandomSampleGenerator();

        comment.setTicket(ticketBack);
        assertThat(comment.getTicket()).isEqualTo(ticketBack);

        comment.ticket(null);
        assertThat(comment.getTicket()).isNull();
    }
}
