package br.com.efraimgentil.speakwithme.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

import br.com.efraimgentil.speakwithme.model.constants.UserType;

public class User implements Serializable , Validator<User> {
  
  private static final long serialVersionUID = -4292493533757480912L;
  
  private Integer id;
  private String _id;
  private String username;
  private String email;
  private String password;
  private String passwordConfirmation;
  private UserType userType;
 
  public User() {  }
  
  public User(String email, String password) {
    super();
    this.email = email;
    this.password = password;
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("User [id=").append(id).append(", username=").append(username)
        .append(", email=").append(email).append(", password=").append(password)
        .append(", userType=").append(userType).append("]");
    return builder.toString();
  }

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public UserType getUserType() {
    return userType;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }
  
  public String getPasswordConfirmation() {
    return passwordConfirmation;
  }

  public void setPasswordConfirmation(String passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
  }

  @Override
  public Map<String, String> validate() {
    Map<String, String> errorMap = new HashMap<String, String>();
    
    if(username == null || username.trim().isEmpty() ){
      errorMap.put("username", "Username should not be empty");
    }else if( username.length() < 4){
      errorMap.put("username", "Username should have at least 4 characters");
    }
    
    if(email == null || email.trim().isEmpty() ){
      errorMap.put("email", "Email should not be empty");
    }
    
    if( password == null || password.trim().isEmpty() ){
      errorMap.put("password", "Password should not be empty");
    }
    
    if(passwordConfirmation != null && !password.equals(passwordConfirmation)){
      errorMap.put("passwordConfirmation", "Password confirmation does not match");
    }
    
    return errorMap;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  
}