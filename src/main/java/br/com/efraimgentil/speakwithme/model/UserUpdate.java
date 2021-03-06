package br.com.efraimgentil.speakwithme.model;

import java.util.List;

import br.com.efraimgentil.speakwithme.model.constants.UpdateType;

public class UserUpdate {
  
  private String id;
  private String userName;
  private String email;
  private String status;
  private List<Message> messages;
  private UpdateType updateType;

  public UserUpdate() {  }
  
  public UserUpdate(String id, String userName) {
    super();
    this.id = id;
    this.userName = userName;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((userName == null) ? 0 : userName.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserUpdate other = (UserUpdate) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (userName == null) {
      if (other.userName != null)
        return false;
    } else if (!userName.equals(other.userName))
      return false;
    return true;
  }
  
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public List<Message> getMessages() {
    return messages;
  }
  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UpdateType getUpdateType() {
    return updateType;
  }

  public void setUpdateType(UpdateType updateType) {
    this.updateType = updateType;
  }
  
}