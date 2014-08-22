package br.com.efraimgentil.speakwithme.model.constants;

public enum UserType {
  
  OWNER("OWNER"),
  GUEST("GUEST");
  
  private String value;
  
  private UserType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
  
}
