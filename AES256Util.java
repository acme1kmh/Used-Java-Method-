package com.lguplus.vr.admin.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES256Util {
	private String iv;
	private Key keySpec;
	
	
	public AES256Util(String key) throws UnsupportedEncodingException {
		this.iv = key.substring(0,  16);
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if(len>keyBytes.length){
			len = keyBytes.length;
		}
		System.arraycopy(b,  0, keyBytes,  0 ,  len);
		SecretKeySpec keySpec  = new SecretKeySpec(keyBytes, "AES");
				
		this.keySpec = keySpec;
	}
	
	public String encrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
		
		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(Base64.encodeBase64(encrypted));
		return enStr;
	}
	
	public String decrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
		byte[] byteStr = Base64.decodeBase64(str.getBytes());
		return new String(c.doFinal(byteStr), "UTF-8");
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException {
		AES256Util aes = new AES256Util("UPLUSVRAES256KEY");   // 암호화 키 16자리
			
		// 암호화 된 내용
		System.out.println("ctn : " + aes.encrypt("012345678912"));  
		System.out.println("uvradm2# : " + aes.decrypt("pYrk2MvWkwN8zIuiikKb5Q=="));
	}
}