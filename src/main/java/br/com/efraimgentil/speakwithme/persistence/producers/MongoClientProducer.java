package br.com.efraimgentil.speakwithme.persistence.producers;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import com.mongodb.MongoClient;

@ApplicationScoped
public class MongoClientProducer {
  
  private MongoClient mongoClient;
  
  @PostConstruct
  public void initiate() throws UnknownHostException{
    System.out.println("########## DA HADUKEN RIU");
    mongoClient = new MongoClient("127.0.0.1", 27017);
  }
  
  @Produces
  public MongoClient produceConnectionPool() throws UnknownHostException {
    if(mongoClient == null)
      initiate();
    return mongoClient;
  }
  
  public void destroyConnectionPool(@Disposes MongoClient  mongoClient){
    mongoClient.close();
  }

}
