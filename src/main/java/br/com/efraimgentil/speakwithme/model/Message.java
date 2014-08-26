package br.com.efraimgentil.speakwithme.model;

import java.io.Serializable;
import java.util.Date;

import br.com.efraimgentil.speakwithme.model.constants.MessageLevel;

public class Message implements Serializable {

  private static final long serialVersionUID = -5717764420030621011L;
  
  private String userId;
  private String userWhoSend;
  private Date date;
  private String body;
  private MessageLevel level;
 
  public Message() {  }
  
  public Message(String userWhoSend, Date date, String body) {
    super();
    this.userWhoSend = userWhoSend;
    this.date = date;
    this.body = body;
  }
  
  public static Message infoMessage(String body){
    Message m = new Message();
    m.userWhoSend = "SYSTEM";
    m.date = new Date();
    m.body = body;
    m.level = MessageLevel.INFO;
    return m;
  }
  
  public static Message userMessage(String username , String body){
    Message m = new Message();
    m.userWhoSend = username;
    m.date = new Date();
    m.body = body;
    m.level = MessageLevel.SENDER;
    return m;
  }
  
  public static Message userMessage(String username , String userId , String body){
    Message m = new Message();
    m.userWhoSend = username;
    m.userId = userId;
    m.date = new Date();
    m.body = body;
    m.level = MessageLevel.DESTINATARY;
    return m;
  }
  

  public String getUserWhoSend() {
    return userWhoSend;
  }
  public void setUserWhoSend(String userWhoSend) {
    this.userWhoSend = userWhoSend;
  }
  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public String getBody() {
    return body;
  }
  public void setBody(String body) {
    this.body = body;
  }
  public MessageLevel getLevel() {
    return level;
  }
  public void setLevel(MessageLevel level) {
    this.level = level;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}