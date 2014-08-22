package br.com.efraimgentil.speakwithme.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import br.com.efraimgentil.speakwithme.model.constants.SessionKeys;
import br.com.efraimgentil.speakwithme.model.constants.WsSessionKeys;

public class CustomConfigurator extends ServerEndpointConfig.Configurator {
  
  @Override
  public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
    System.out.println("HANDSHAKE");
    HttpSession httpSession = (HttpSession) request.getHttpSession();
    if( httpSession.getAttribute( SessionKeys.IS_LOGGED ) != null ){
      sec.getUserProperties().put( WsSessionKeys.HTTP_SESSION ,  httpSession );
    }else{
//      response.getHeaders().put( response.SEC_WEBSOCKET_ACCEPT , new ArrayList<String>() );
      throw new RuntimeException(" NIH ");
    }
  }
  
  
}
