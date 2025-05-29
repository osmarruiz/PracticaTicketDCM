package com.practicaticket.dcm.client;

import java.lang.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@FeignClient
public @interface AuthorizedFeignClient {
    @AliasFor(annotation = FeignClient.class, attribute = "name")
    String name() default "";

    /**
     * A custom {@code @Configuration} for the feign client.
     *
     * Can contain override {@code @Bean} definition for the pieces that
     * make up the client, for instance {@link feign.codec.Decoder},
     * {@link feign.codec.Encoder}, {@link feign.Contract}.
     *
     * @return the custom {@code @Configuration} for the feign client.
     * @see org.springframework.cloud.openfeign.FeignClientsConfiguration for the defaults.
     */
    @AliasFor(annotation = FeignClient.class, attribute = "configuration")
    Class<?>[] configuration() default OAuth2InterceptedFeignConfiguration.class;

    /**
     * An absolute URL or resolvable hostname (the protocol is optional).
     * @return the URL.
     */
    @AliasFor(annotation = FeignClient.class, attribute = "url")
    String url() default "";

    /**
     * Whether 404s should be decoded instead of throwing FeignExceptions.
     * @return true if 404s will be decoded; false otherwise.
     */
    @AliasFor(annotation = FeignClient.class, attribute = "dismiss404")
    boolean dismiss404() default false;

    /**
     * Fallback class for the specified Feign client interface. The fallback class must
     * implement the interface annotated by this annotation and be a valid Spring bean.
     * @return the fallback class for the specified Feign client interface.
     */
    @AliasFor(annotation = FeignClient.class, attribute = "fallback")
    Class<?> fallback() default void.class;

    /**
     * Path prefix to be used by all method-level mappings.
     * @return the path prefix to be used by all method-level mappings.
     */
    @AliasFor(annotation = FeignClient.class, attribute = "path")
    String path() default "";
}
