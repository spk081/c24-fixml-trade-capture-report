package biz.c24.io.fixml.sample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 03/04/2013
 */
@ImportResource(value = {
        "classpath:META-INF/spring/gem.xml",
        "classpath:META-INF/spring/gem-exception-management.xml",
        "classpath:META-INF/spring/gem-store-compass.xml"
})
@Configuration
public class C24GemfireConfiguration {

}