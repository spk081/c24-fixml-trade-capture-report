package biz.c24.io.fixml.sample.configuration;

import biz.c24.io.api.presentation.JsonSource;
import biz.c24.io.fixml.sample.converter.XMLConverter;
import biz.c24.io.fixml.sample.enricher.CdoEnricher;
import biz.c24.io.fixml.sample.reader.MongoDbReader;
import biz.c24.io.fixml.sample.storage.CompassFileStore;
import biz.c24.io.spring.core.C24Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import java.io.File;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 03/04/2013
 */
@ImportResource(value = {
        "classpath:META-INF/spring/flow-db-read.xml"
})
@Import({
        C24ExternalPropertiesConfiguration.class,
        C24MongoDbConfiguration.class,
        C24iOConfiguration.class
})

@Configuration
public class C24MongoDbPollerConfiguration {

    @Value("${file.source.dir")
    private String fileSource;
    @Value("${file.compass.valid.dir}")
    private String validDir;
    @Value("${file.compass.valid.dir}")
    private String invalidDir;

    @Autowired private JsonSource jsonSource;
    @Autowired private C24Model fixmlModel;


    @Bean(name = "parse-json")
    public MongoDbReader getMongoDbReader()
    {
        MongoDbReader mongoReader = new MongoDbReader(jsonSource, fixmlModel);
        return mongoReader;
    }

    @Bean(name = "header-enricher")
    public CdoEnricher getEnricher()
    {
        return new CdoEnricher();
    }

    @Bean(name = "xml-converter")
    public XMLConverter getXMLConverter()
    {
        return new XMLConverter();
    }

    @Bean(name = "compass-file-store")
    public CompassFileStore getFileUtils()
    {
        return new CompassFileStore(getValidDir(), getInvalidDir());
    }

    @Bean
    public File getValidDir()
    {
        return new File(validDir);
    }
    @Bean
    public File getInvalidDir()
    {
        return new File(invalidDir);
    }
}