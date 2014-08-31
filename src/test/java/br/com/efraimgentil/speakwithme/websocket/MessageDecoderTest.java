package br.com.efraimgentil.speakwithme.websocket;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageDecoderTest {
  
  IncomingMessageDecoder decoder;
  
  @Before
  public void setUp(){
    decoder = new IncomingMessageDecoder();
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
