package me.sigh.commons.utils.sequence;

import java.util.UUID;

public class SequenceGenerator {

  public static String randomUUID(){
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
  
  public static void main(String[] args) {
    System.out.println(randomUUID());
  }
}
