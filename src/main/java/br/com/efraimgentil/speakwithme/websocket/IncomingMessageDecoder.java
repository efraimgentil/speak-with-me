package br.com.efraimgentil.speakwithme.websocket;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import br.com.efraimgentil.speakwithme.model.IncomingMessage;

public class IncomingMessageDecoder implements Decoder.Text<IncomingMessage> {

  public void init(EndpointConfig config) {
    // TODO Auto-generated method stub
  }

  public void destroy() {
    // TODO Auto-generated method stub
  }

  @SuppressWarnings("rawtypes")
  public IncomingMessage decode(String s) throws DecodeException {
    IncomingMessage incomingMessage = null;

    for ( Text text : decodersList() ) {
      if( text.willDecode(s) ) {
        Object message = text.decode(s);
        incomingMessage = new IncomingMessage(message);
        break;
      }
    }
    
    return incomingMessage;
  }

  public boolean willDecode(String s) {
    if(s.toLowerCase().contains("\"type\":\"message\"")){
      return true;
    }
    return true;
  }
  
  @SuppressWarnings("rawtypes")
  protected List<Decoder.Text> decodersList(){
    List<Decoder.Text> decoders = new ArrayList<>();
    decoders.add( new MessageDecoder() );
    return decoders;
  }
  
}
