package br.com.efraimgentil.speakwithme.model;

import java.util.List;

public class Guest {
  
  private String id;
  private String userName;
  private String status;
  private List<Message> messages;

  public Guest() {  }
  
  public Guest(String id, String userName) {
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
    Guest other = (Guest) obj;
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
  
}