package br.com.efraimgentil.speakwithme.model;

import br.com.efraimgentil.speakwithme.model.constants.MessageType;

public class IncomingMessage {
 
  private User user;
  private Object message;
  private MessageType messageType;
  
  public IncomingMessage(Object message) {
    super();
    this.message = message;
    if(isMessage()){
      messageType = MessageType.MESSAGE;
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("IncomingMessage [user=").append(user).append(", message=").append(message)
        .append(", messageType=").append(messageType).append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((message == null) ? 0 : message.hashCode());
    result = prime * result + ((messageType == null) ? 0 : messageType.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
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
    IncomingMessage other = (IncomingMessage) obj;
    if (message == null) {
      if (other.message != null)
        return false;
    } else if (!message.equals(other.message))
      return false;
    if (messageType != other.messageType)
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }

  public Object getMessage() {
    return message;
  }

  public void setMessage(Object message) {
    this.message = message;
  }

  public MessageType getMessageType() {
    return messageType;
  }

  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }
  
  public boolean isMessage(){
    return message instanceof Message;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
