package com.practicaticket.dcm;

import com.practicaticket.dcm.config.AsyncSyncConfiguration;
import com.practicaticket.dcm.config.EmbeddedRedis;
import com.practicaticket.dcm.config.EmbeddedSQL;
import com.practicaticket.dcm.config.JacksonConfiguration;
import com.practicaticket.dcm.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = { PracticaTicketDcmApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class }
)
@EmbeddedRedis
@EmbeddedSQL
public @interface IntegrationTest {
}
