package br.com.efraimgentil.speakwithme.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import br.com.efraimgentil.speakwithme.model.Guest;
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
      List<Session> onlineGuests = new ArrayList<>();
      for (Session otherSession : session.getOpenSessions()) {
        if(!otherSession.getId().equals( session.getId() )){
          otherSession.getBasicRemote().sendObject( Message.infoMessage("The chat owner is now online") );
          onlineGuests.add(otherSession);
        }
      }
      List<Guest> guests = new ArrayList<>();
      for (Session sessionGuest : onlineGuests) {
        HttpSession httpSession = (HttpSession) sessionGuest.getUserProperties().get( WsSessionKeys.HTTP_SESSION );
        User guestUser =  (User) httpSession.getAttribute( SessionKeys.USER_AUTHENTICATED );
        Guest guest = new Guest( sessionGuest.getId() , guestUser.getUsername() );
        guests.add(guest);
      }
      if(!guests.isEmpty()){
        basicRemote.sendObject( guests );
      }
    }else{
      if(owner == null){
        basicRemote.sendObject( Message.infoMessage("Sorry to inform you but owner of this chat is offline") );
      }
    }
  }

  public void receiveMessage(String message , Session session ) throws IOException, EncodeException{
    HttpSession httpSession = (HttpSession) session.getUserProperties().get( WsSessionKeys.HTTP_SESSION );
    User user = (User) httpSession.getAttribute( SessionKeys.USER_AUTHENTICATED );
    String username = user.getUsername();
    session.getBasicRemote().sendObject( Message.userMessage(username, message) );
    if(owner != null){
      
    }else{
      session.getUserProperties().put( WsSessionKeys.OWNER_NOT_LOGGED , true );
      session.getBasicRemote().sendObject( Message.infoMessage("The owner is not logged in, your message will be delivered when him enter") );
    }
  }
//  public void receiveMessage(String message , Session session ) throws IOException{
//    String userId = (String) session.getUserProperties().get("USER_ID");
//    User user = userSession.getUser();
//    if( isOwner( userSession.getUser() ) ){
//      //OWNER SEND MESSAGE TO A SPECIFIC USER
//    }else{
//      Basic basicRemote = owner.getSession().getBasicRemote();
//      if( owner.getSession().isOpen() ){
//        basicRemote.sendText( createMessage(message, user) );
//      }else{
//        //MSG ERROR OWNER IS NOT LOGGED IN
//      }
//    }
//  }
  
  private String createMessage(String message , User user ){
    return null;
  }
  
  private boolean isOwner(User user){
    return UserType.OWNER.equals( user.getUserType() );
  }

}