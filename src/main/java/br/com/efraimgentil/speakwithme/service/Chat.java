package br.com.efraimgentil.speakwithme.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import br.com.efraimgentil.speakwithme.model.Guest;
import br.com.efraimgentil.speakwithme.model.IncomingMessage;
import br.com.efraimgentil.speakwithme.model.Message;
import br.com.efraimgentil.speakwithme.model.User;
import br.com.efraimgentil.speakwithme.model.constants.SessionKeys;
import br.com.efraimgentil.speakwithme.model.constants.UserType;
import br.com.efraimgentil.speakwithme.model.constants.WsSessionKeys;

public class Chat {
  
  private Session owner;
  
  public void connectUser( User user , Session session ) throws IOException, EncodeException{
    Basic basicRemote = session.getBasicRemote();
    basicRemote.sendObject( Message.infoMessage("You are now connected") );
    
    if( isOwner(user) ){
      owner = session;
      List<Session> guestsSessions = retriveGuestSession(session);
      List<Guest> guests = extractGuests(guestsSessions);
      if(!guests.isEmpty()){
        sendToSessions(guestsSessions, Message.infoMessage("The owner is now online") );
        sendToOwner( guests );
      }
    }else{
      if(owner == null){
        basicRemote.sendObject( Message.infoMessage("Sorry to inform you but owner of this chat is offline") );
      }else{
        Guest guest = new Guest( session.getId() , user.getUsername() );
        sendToOwner(guest);
      }
    }
  }
  
  private List<Guest> extractGuests(List<Session> sessions){
    List<Guest> guests = new ArrayList<>();
    for (Session sessionGuest : sessions) {
      User guestUser =  extranctUser(sessionGuest); 
      Guest guest = new Guest( sessionGuest.getId() , guestUser.getUsername() );
      guests.add(guest);
    }
    return guests;
  }
  
  private List<Session> retriveGuestSession(Session session){
    List<Session> onlineGuests = new ArrayList<>();
    for (Session otherSession : session.getOpenSessions()) {
      if(!otherSession.getId().equals( session.getId() )){
        onlineGuests.add(otherSession);
      }
    }
    return onlineGuests;
  }
  
  private <T> void sendToOwner(T anything) throws IOException, EncodeException{
    if(owner != null){
      owner.getBasicRemote().sendObject( anything );
    }
  }
  
  private <T> void sendToSessions(List<Session> sessions , T message) throws IOException, EncodeException{
    for (Session session : sessions) {
      session.getBasicRemote().sendObject(message);
    }
  }
  
  private User extranctUser(Session session){
    HttpSession httpSession = (HttpSession) session.getUserProperties().get( WsSessionKeys.HTTP_SESSION );
    return (User) httpSession.getAttribute( SessionKeys.USER_AUTHENTICATED );
  }

  public void receiveMessage(String message , Session session ) throws IOException, EncodeException{
    HttpSession httpSession = (HttpSession) session.getUserProperties().get( WsSessionKeys.HTTP_SESSION );
    User user = (User) httpSession.getAttribute( SessionKeys.USER_AUTHENTICATED );
    String username = user.getUsername();
    session.getBasicRemote().sendObject( Message.userMessage(username, message) );
    if(owner != null){
      owner.getBasicRemote().sendObject( Message.userMessage( user.getUsername() , session.getId()  , message ) );
    }else{
      session.getUserProperties().put( WsSessionKeys.OWNER_NOT_LOGGED , true );
      session.getBasicRemote().sendObject( Message.infoMessage("The owner is not logged in, your message will be delivered when him enter") );
    }
  }

  private boolean isOwner(User user){
    return UserType.OWNER.equals( user.getUserType() );
  }

  public void receiveMessage(IncomingMessage incomingMessage, Session session) throws IOException, EncodeException {
    String username = incomingMessage.getUser().getUsername();
    if(incomingMessage.isMessage()){
      Message message = (Message) incomingMessage.getMessage();
      session.getBasicRemote().sendObject( Message.userMessage(username, message.getBody() ) );
      if(owner != null){
        owner.getBasicRemote().sendObject( Message.userMessage( username , session.getId()  , message.getBody() ) );
      }else{
        session.getUserProperties().put( WsSessionKeys.OWNER_NOT_LOGGED , true );
        session.getBasicRemote().sendObject( Message.infoMessage("The owner is not logged in, your message will be delivered when him enter") );
      }
    }
  }

}