package com.practicaticket.dcm.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.apidoc.customizer.JHipsterOpenApiCustomizer;

@Configuration
@Profile(JHipsterConstants.SPRING_PROFILE_API_DOCS)
public class OpenApiConfiguration {

    public static final String API_FIRST_PACKAGE = "com.practicaticket.dcm.web.rest";

    @Bean
    @ConditionalOnMissingBean(name = "apiFirstGroupedOpenAPI")
    public GroupedOpenApi apiFirstGroupedOpenAPI(
        JHipsterOpenApiCustomizer jhipsterOpenApiCustomizer,
        JHipsterProperties jHipsterProperties
    ) {
        JHipsterProperties.ApiDocs properties = jHipsterProperties.getApiDocs();
        
        return GroupedOpenApi.builder()
            .group("default")
            .addOpenApiCustomizer(jhipsterOpenApiCustomizer)
            .packagesToScan(API_FIRST_PACKAGE)
            .pathsToMatch(properties.getDefaultIncludePattern())
            
            .build();
    }

    
	@Bean
	public OpenAPI customOpenAPI() {
		final String securitySchemeName = "bearerAuth";

		return new OpenAPI().info(new Info().title("Practica API").version("1.0").description("DCM Consulting API"))
				.components(new Components().addSecuritySchemes(securitySchemeName,
						new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer")
								.bearerFormat("JWT")))
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
	}
    
}
