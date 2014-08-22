package br.com.efraimgentil.speakwithme.model;

import java.io.Serializable;

import br.com.efraimgentil.speakwithme.model.constants.UserType;

public class User implements Serializable {
  
  private static final long serialVersionUID = -4292493533757480912L;
  
  private Integer id;
  private String username;
  private String email;
  private String password;
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
}