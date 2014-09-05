package br.com.efraimgentil.speakwithme.persistence.producers;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoConnectionProducerTest {
  
  MongoClientProducer clientProducer;
  
  @Before
  public void setUp(){
    clientProducer = new MongoClientProducer();
  }
  
  @Test
  public void doesCreateAMongoDBConnection() throws UnknownHostException{
    MongoClient client = clientProducer.produceConnectionPool();
    assertNotNull("Should have created a connection with the MongoDB, verify if the connection is disponible", client); 
  }
  
  
}
