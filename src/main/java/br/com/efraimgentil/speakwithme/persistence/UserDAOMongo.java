package br.com.efraimgentil.speakwithme.persistence;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.efraimgentil.speakwithme.model.User;
import br.com.efraimgentil.speakwithme.model.constants.UserType;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;


public class UserDAOMongo implements UserDAO {
  
  @Inject
  private MongoClient client;
  private DBCollection dbCollection;
  
  @PostConstruct
  public void init(){
    DB dataBase = client.getDB("speakwithme");
    dbCollection = dataBase.getCollection("user");
  }
  
  public UserDAOMongo() {  }

  @Override
  public User userByEmailAndPassword(String email, String password) {
    BasicDBObject documentDetail = new BasicDBObject();
    documentDetail.put("email", email);
    documentDetail.put("password", password);
    DBCursor find = dbCollection.find( documentDetail );
    if(find.hasNext()){
      return translateToUser( find.next() );
    }
    return null;
  }

  @Override
  public User persist(User user) {
    BasicDBObject documentDetail = new BasicDBObject();
    documentDetail.put("username", user.getUsername());
    documentDetail.put("email", user.getEmail());
    documentDetail.put("password", user.getPassword());
    documentDetail.put("userType",  user.getUserType().toString() );
    WriteResult result = dbCollection.insert(documentDetail);
    Object _id = result.getField("_id");
    user.set_id( _id.toString() );
    return user;
  }
  
  protected User translateToUser(DBObject dbObject){
    User user = new User();
    user.setUsername( String.valueOf( dbObject.get("username") ) );
    user.setEmail( String.valueOf( dbObject.get("email") ) );
    user.set_id( String.valueOf( dbObject.get("_id") ) );
    user.setUserType( UserType.valueOf( String.valueOf( dbObject.get("userType") ) ));
    return user;
  }  
  

}
