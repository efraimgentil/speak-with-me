package br.com.efraimgentil.speakwithme.websocket;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import br.com.efraimgentil.speakwithme.model.IncomingMessage;
import br.com.efraimgentil.speakwithme.model.Message;
import br.com.efraimgentil.speakwithme.model.User;
import br.com.efraimgentil.speakwithme.model.constants.SessionKeys;
import br.com.efraimgentil.speakwithme.model.constants.WsSessionKeys;
import br.com.efraimgentil.speakwithme.service.Chat;

@ServerEndpoint(value = "/speak/"
, encoders = { MessageEncoder.class  , GuestsEncoder.class , GuestEncoder.class }
, decoders = { IncomingMessageDecoder.class , StringDecoder.class }
, configurator = CustomConfigurator.class)
public class SpeakWithMe {
  
  private static final Chat chat;
  static{
    chat = new Chat();
  }
  
  @OnOpen
  public void open(Session session , EndpointConfig config){
    System.out.println("OPEN");
    HttpSession httpSession =  (HttpSession) config.getUserProperties().get( WsSessionKeys.HTTP_SESSION );
    session.getUserProperties().put( WsSessionKeys.HTTP_SESSION , httpSession );
    User user = (User) httpSession.getAttribute( SessionKeys.USER_AUTHENTICATED );
    try {
      chat.connectUser(user, session);
    } catch (IOException | EncodeException e) {
      e.printStackTrace();
    }
  }
  
  @OnMessage
  public void receiveAsString(IncomingMessage incomingMessage, Session session){
    incomingMessage.setUser( extranctUser(session) );
    System.out.println( incomingMessage );
    try {
        chat.receiveMessage( incomingMessage , session);
    } catch (IOException | EncodeException e) {
      e.printStackTrace();
    }
  }
  
//  @OnMessage
//  public void receiveAsMessage(Message message, Session session){
//    System.out.println("AS MESSAGE");
//    try {
//      System.out.println( message);
////      chat.receiveMessage(message, session);
//    } catch (IOException | EncodeException e) {
//      e.printStackTrace();
//    }
//  }
  
  @OnError
  public void error(Throwable e){
    System.out.println( "Sorry but there is a error, closing!" );
    System.out.println( e.getMessage() );
  }
  
  private User extranctUser(Session session){
    HttpSession httpSession = (HttpSession) session.getUserProperties().get( WsSessionKeys.HTTP_SESSION );
    return (User) httpSession.getAttribute( SessionKeys.USER_AUTHENTICATED );
  }

}