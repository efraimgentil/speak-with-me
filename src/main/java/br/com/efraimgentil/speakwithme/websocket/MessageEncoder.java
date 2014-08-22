package br.com.efraimgentil.speakwithme.websocket;

import java.io.StringWriter;
import java.text.SimpleDateFormat;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import br.com.efraimgentil.speakwithme.model.Message;

public class MessageEncoder implements Encoder.Text<Message> {

//  @Override
  public String encode(Message object) throws EncodeException {
    StringWriter writer = new StringWriter();
    JsonGenerator generator = Json.createGenerator(writer);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    generator.writeStartObject();
    generator.write( "userWhoSend" , object.getUserWhoSend() );
    generator.write( "date" ,  sdf.format( object.getDate() ) );
    generator.write( "body" , object.getBody() );
    generator.write( "type" , object.getType().toString() );
    generator.writeEnd().flush();
    return writer.toString();
  }

  public void init(EndpointConfig config) {
    // TODO Auto-generated method stub
    
  }

  public void destroy() {
    // TODO Auto-generated method stub
    
  }

}
