package biz.c24.io.fixml.sample.configuration;

import biz.c24.io.api.presentation.JsonSink;
import biz.c24.io.api.presentation.JsonSource;
import biz.c24.io.fixml.sample.storage.MongoDbWriter;
import biz.c24.io.fixml.sample.storage.MongoDbWriterImpl;
import biz.c24.io.spring.core.C24Model;
import biz.c24.io.spring.source.XmlSourceFactory;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.smoke.test.ConsolePrinter;
import org.fixprotocol.fixml44.FIXMLElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.net.UnknownHostException;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 03/04/2013
 */
@ImportResource(value = {
        "classpath:META-INF/spring/flow-config.xml"
})
@Configuration
public class C24iOConfiguration {

    @Value("${mongo.db.server}")
    private String mongoServer;
    @Value("${mongo.db.port}")
    private int mongoPort;
    @Value("${mongo.db.name}")
    private String mongoDBName;
    @Value("${mongo.collection.messages.name}")
    private String mongoCollectionName;

    @Bean(name = "debug")
    public ConsolePrinter getConsolePrinter() {
        return new ConsolePrinter();
    }

    @Bean(name = "mongoDbFixMlCollectionWriter")
    public MongoDbWriter getMongoDbWriter() throws UnknownHostException {
        MongoDbWriter mongoDbWriter = new MongoDbWriterImpl(getFIXMLCollection(), getJsonSink());
        return mongoDbWriter;
    }

    @Bean(name = "xmlSourceFactory")
    public XmlSourceFactory getTextualSourceFactory() {
        XmlSourceFactory xmlSourceFactory = new XmlSourceFactory();
        xmlSourceFactory.setEncoding("UTF-8");
        return xmlSourceFactory;
    }

    @Bean(name = "fixmlModel")
    public C24Model getFixmlModel() {
        return new C24Model(getFIXMLElement());
    }

    @Bean
    public FIXMLElement getFIXMLElement() {
        return new FIXMLElement();
    }

    @Bean
    public MongoClient getMongoClient() throws UnknownHostException {
        return new MongoClient(mongoServer, mongoPort);
    }

    @Bean
    public JsonSink getJsonSink() {
        return new JsonSink();
    }

    @Bean
    public JsonSource getJsonSource() {
        return new JsonSource();
    }

    @Bean(name = "fixmlCollection")
    public DBCollection getFIXMLCollection() throws UnknownHostException {
        MongoClient mongoClient = getMongoClient();
        return mongoClient.getDB(mongoDBName).getCollection(mongoCollectionName);
    }
}