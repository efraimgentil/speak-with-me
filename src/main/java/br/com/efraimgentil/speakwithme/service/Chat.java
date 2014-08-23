package br.com.efraimgentil.speakwithme.service;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import br.com.efraimgentil.speakwithme.model.Message;
import br.com.efraimgentil.speakwithme.model.User;
import br.com.efraimgentil.speakwithme.model.constants.SessionKeys;
import br.com.efraimgentil.speakwithme.model.constants.UserType;
import br.com.efraimgentil.speakwithme.model.constants.WsSessionKeys;

public class Chat {
  
  private Session owner;
  
  public void connectUser( User user , Session session ) throws IOException, EncodeException{
    session.getBasicRemote().sendObject( Message.infoMessage("You are now connected") );
    
    if( isOwner(user) ){
      owner = session;
      for (Session otherSession : session.getOpenSessions()) {
        if(!otherSession.getId().equals( session.getId() )){
          otherSession.getBasicRemote().sendObject( Message.infoMessage("The chat owner is now online") );
        }
      }
    }else{
      if(owner == null){
        session.getBasicRemote().sendObject( Message.infoMessage("Sorry to inform you but owner of this chat is offline") );
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