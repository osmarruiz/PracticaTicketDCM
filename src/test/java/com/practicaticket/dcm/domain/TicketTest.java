package com.practicaticket.dcm.domain;

import static com.practicaticket.dcm.domain.CategoryTestSamples.*;
import static com.practicaticket.dcm.domain.TicketTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.practicaticket.dcm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ticket.class);
        Ticket ticket1 = getTicketSample1();
        Ticket ticket2 = new Ticket();
        assertThat(ticket1).isNotEqualTo(ticket2);

        ticket2.setId(ticket1.getId());
        assertThat(ticket1).isEqualTo(ticket2);

        ticket2 = getTicketSample2();
        assertThat(ticket1).isNotEqualTo(ticket2);
    }

    @Test
    void categoryTest() {
        Ticket ticket = getTicketRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        ticket.setCategory(categoryBack);
        assertThat(ticket.getCategory()).isEqualTo(categoryBack);

        ticket.category(null);
        assertThat(ticket.getCategory()).isNull();
    }
}
