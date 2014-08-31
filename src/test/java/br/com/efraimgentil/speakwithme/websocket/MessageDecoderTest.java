package br.com.efraimgentil.speakwithme.websocket;

import javax.websocket.DecodeException;

import org.junit.Before;
import org.junit.Test;

import br.com.efraimgentil.speakwithme.model.Message;
import static org.junit.Assert.*;

public class MessageDecoderTest {
  
  MessageDecoder decoder;
  
  @Before
  public void setUp(){
    decoder = new MessageDecoder();
  }
  
  @Test
  public void doesReturnAMessageObjectWithTheJsonDataParsed() throws DecodeException{
    String data = "{\"type\":\"message\" , \"body\":\"Teste mensagem\" }";
    Message decode = decoder.decode(data);
    
    assertEquals("Teste mensagem", decode.getBody() );
    assertNull("Should not define destinataryId if it is not in json" , decode.getDestinataryId() );
  }
  
  @Test
  public void doesReturnAMessageObjectWithDestinataryIdAndJsonDataParsed() throws DecodeException{
    String data = "{\"type\":\"message\" , \"body\":\"Teste mensagem\" , \"destinataryId\":\"123456\" }";
    Message decode = decoder.decode(data);
    
    assertEquals("Teste mensagem", decode.getBody() );
    assertEquals("123456" , decode.getDestinataryId() );
  }
  
  @Test
  public void doesReturnTrueWhenTheIncomingDataIsAMessage(){
    String data = "{\"type\":\"message\" }";
    
    assertTrue("Should be true when the data is a message ( 'type':'message' )" , decoder.willDecode( data ));
  }
  
  @Test
  public void doesReturnFalseWhenTheIncomingDataIsNotAMessage(){
    String data = "{\"type\":\"update\"}";
    
    assertFalse("Should be false because it is not a message ( 'type':'NOT_A_MESSAGE' )" , decoder.willDecode( data ));
  }
  
  @Test
  public void doesReturnFalseWhenTheIncomingDataIsEmpty(){
    String data = "";
    
    assertFalse("Should be false because it is not a message ( 'type':'NOT_A_MESSAGE' )" , decoder.willDecode( data ));
  }
  
}
