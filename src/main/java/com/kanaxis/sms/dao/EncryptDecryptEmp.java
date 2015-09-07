package com.kanaxis.sms.dao;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptDecryptEmp {
	
	public static class StrongAES {
		
		 
		
		    public void run() {
		
		      try {
		
		         String text = "123456";
		
		         String key = "MTIzNDU2"; // 128 bit key
		         byte[]   bytesEncoded = Base64.encodeBase64(text .getBytes());
		         System.out.println("ecncoded value is " + new String(bytesEncoded ));
		
		         byte[] valueDecoded= Base64.decodeBase64(key.getBytes() );
		         System.out.println("Decoded value is " + new String(valueDecoded));
		
		         /*// Create key and cipher
		
		         Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
		
		         Cipher cipher = Cipher.getInstance("AES");
		
		 
		
		         // encrypt the text
		
		         cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		
		         byte[] encrypted = cipher.doFinal(text.getBytes());
		
		         System.err.println(new String(encrypted));
		
		 
		
		         // decrypt the text
		
		         cipher.init(Cipher.DECRYPT_MODE, aesKey);
		
		         String decrypted = new String(cipher.doFinal(encrypted));
		
		         System.err.println(decrypted);*/
		
		      }catch(Exception e) {
		
		         e.printStackTrace();
		
		      }
		
		    }
		
		 
		
		    public static void main(String[] args) {
		
		       StrongAES app = new StrongAES();
		
		       app.run();
		
		    }


}
	
}
