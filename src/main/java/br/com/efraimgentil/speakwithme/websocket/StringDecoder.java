package br.com.efraimgentil.speakwithme.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import br.com.efraimgentil.speakwithme.model.Message;

public class StringDecoder implements Decoder.Text<String> {

  public void init(EndpointConfig config) {
    // TODO Auto-generated method stub
  }

  public void destroy() {
    // TODO Auto-generated method stub
  }

  public String decode(String s) throws DecodeException {
    return s;
  }

  public boolean willDecode(String s) {
    return true;
  }

}
