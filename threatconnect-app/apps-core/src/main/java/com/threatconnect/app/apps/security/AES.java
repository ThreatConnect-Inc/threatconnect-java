package com.threatconnect.app.apps.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

public class AES
{
	private static final String KEY_SPEC = "AES";
	private static final String CIPHER = "AES/CBC/PKCS5Padding";
	
	public static byte[] encrypt(final String content, final String secret) throws GeneralSecurityException
	{
		final SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), KEY_SPEC);
		
		final byte[] iv = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		
		Cipher cipher = Cipher.getInstance(CIPHER);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
		return cipher.doFinal(content.getBytes());
	}
	
	public static String decrypt(final byte[] content, final String secret) throws GeneralSecurityException
	{
		final SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), KEY_SPEC);
		
		final byte[] iv = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		
		Cipher cipher = Cipher.getInstance(CIPHER);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
		return new String(cipher.doFinal(content));
	}
}
