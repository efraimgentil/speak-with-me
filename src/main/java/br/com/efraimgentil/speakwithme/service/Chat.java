package br.com.efraimgentil.speakwithme.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import br.com.efraimgentil.speakwithme.model.UserUpdate;
import br.com.efraimgentil.speakwithme.model.IncomingMessage;
import br.com.efraimgentil.speakwithme.model.Message;
import br.com.efraimgentil.speakwithme.model.User;
import br.com.efraimgentil.speakwithme.model.constants.MessageLevel;
import br.com.efraimgentil.speakwithme.model.constants.SessionKeys;
import br.com.efraimgentil.speakwithme.model.constants.UpdateType;
import br.com.efraimgentil.speakwithme.model.constants.UserType;
import br.com.efraimgentil.speakwithme.model.constants.WsSessionKeys;

public class Chat {
  
  private Session owner;
  
  public void connectUser( User user , Session session ) throws IOException, EncodeException{
    session.getUserProperties().put(WsSessionKeys.USER_STATUS, "online" );
    Basic basicRemote = session.getBasicRemote();
    basicRemote.sendObject( Message.infoMessage("You are now connected") );

    if( isOwner(user) ){
      owner = session;
      List<Session> guestsSessions = retriveGuestSession(session);
      List<UserUpdate> guests = extractGuests(guestsSessions);
      if(!guests.isEmpty()){
        sendToSessions(guestsSessions, Message.infoMessage("The owner is now online") );
        sendToOwner( guests );
      }
    }else{
      if(owner == null){
        basicRemote.sendObject( Message.infoMessage("Sorry to inform you but owner of this chat is offline") );
      }else{
        UserUpdate guest = new UserUpdate( session.getId() , user.getUsername() );
        guest.setUpdateType(UpdateType.NEW_USER_CONNECTED);
        guest.setEmail( user.getEmail() );
        sendToOwner(guest);
        Message infoMessage = Message.infoMessage( "User is now online" );
        infoMessage.setUserId(session.getId() );
        sendToOwner( infoMessage );
      }
    }
  }
  
  private List<UserUpdate> extractGuests(List<Session> sessions){
    List<UserUpdate> guests = new ArrayList<>();
    for (Session sessionGuest : sessions) {
      User guestUser =  extranctUser(sessionGuest); 
      UserUpdate guest = new UserUpdate( sessionGuest.getId() , guestUser.getUsername() );
      guest.setEmail( guestUser.getEmail() );
      String status = (String)sessionGuest.getUserProperties().get( WsSessionKeys.USER_STATUS );
      guest.setStatus(status);
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
  
  private <T> void sendToSession(T anything, Session session , String destinataryId ) throws IOException, EncodeException{
    List<Session> guests = retriveGuestSession(session);
    for (Session session2 : guests) {
      if(session2.getId().equals(destinataryId)){
        session2.getBasicRemote().sendObject(anything);
        break;
      }
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

  public void handleIncomingMessage(IncomingMessage incomingMessage, Session session) throws IOException, EncodeException {
    String username = incomingMessage.getUser().getUsername();
    if(incomingMessage.isMessage()){
      Message message = (Message) incomingMessage.getMessage();
      Message selfMessage = Message.userMessage(username, message.getDestinataryId() , message.getBody() );
      selfMessage.setLevel( MessageLevel.SENDER );
      session.getBasicRemote().sendObject( selfMessage );

      if(message.getDestinataryId() != null && isOwnerSession(session) ){
        Message userMessage = Message.userMessage(username, message.getDestinataryId() , message.getBody() );
        sendToSession( userMessage , session , message.getDestinataryId());
      }
      
      if(owner != null){
        owner.getBasicRemote().sendObject( Message.userMessage( username , session.getId()  , message.getBody() ) );
      }else{
        session.getUserProperties().put( WsSessionKeys.OWNER_NOT_LOGGED , true );
        session.getBasicRemote().sendObject( Message.infoMessage("The owner is not logged in, your message will be delivered when him enter") );
      }
    }
  }

  public void handleCloseConnection(Session session) throws IOException, EncodeException {
    if(isOwnerSession(session)){
      owner = null;
      List<Session> guestSessions = retriveGuestSession(session);
      Message infoMessage = Message.infoMessage("The owner is now offline");
      for (Session guestSession : guestSessions) {
        guestSession.getBasicRemote().sendObject( infoMessage );
      }
    }else{
      if(owner != null){
        User user = extranctUser(session);
        Message infoMessage = Message.infoMessage(user.getUsername() + " is now offline.");
        infoMessage.setUserId( session.getId() );
        
        Basic basicRemote = owner.getBasicRemote();
        basicRemote.sendObject(infoMessage);
        UserUpdate userUpdate = new UserUpdate( session.getId() , "" );
        userUpdate.setStatus("offline");
        userUpdate.setUpdateType(UpdateType.USER_DISCONNECT);
        basicRemote.sendObject(userUpdate);
      }
    }
  }
  
  protected boolean isOwnerSession(Session session){
    return owner != null && owner.getId().equals( session.getId() );
  }

}