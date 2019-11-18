package com.threatconnect.app.apps.security;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class AESTest
{
	private static final Logger logger = LoggerFactory.getLogger(AESTest.class);
	
	@Test
	public void encryptDecrypt() throws GeneralSecurityException, IOException
	{
		//generate a random key to encrypt the app params
		final String key = RandomStringUtils.randomAlphabetic(16);
		File file = new File("target/" + key + ".txt");
		
		final String original = "Encrypt Me";
		logger.info("Original: {}", original);
		
		try (FileOutputStream fileOutputStream = new FileOutputStream(file))
		{
			byte[] encrypted = AES.encrypt(original, key);
			logger.info("Encrypted: {}", new String(encrypted));
			IOUtils.write(encrypted, fileOutputStream);
			
			final String decrypted = AES.decrypt(encrypted, key);
			logger.info("Decrypted: {}", decrypted);
			
			Assert.assertNotEquals(original, encrypted);
			Assert.assertEquals(original, decrypted);
		}
	}
}
