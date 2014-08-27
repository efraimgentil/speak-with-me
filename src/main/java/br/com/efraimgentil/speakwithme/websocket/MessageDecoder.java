package br.com.efraimgentil.speakwithme.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import br.com.efraimgentil.speakwithme.model.Message;

public class MessageDecoder implements Decoder.Text<Message> {

  public void init(EndpointConfig config) {
    // TODO Auto-generated method stub
  }

  public void destroy() {
    // TODO Auto-generated method stub
  }

  public Message decode(String s) throws DecodeException {
    return null;
  }

  public boolean willDecode(String s) {
    if(s.toLowerCase().contains("\"type\":\"message\"")){
      return true;
    }
    return false;
  }

}
