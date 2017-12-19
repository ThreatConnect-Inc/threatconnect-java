package com.threatconnect.opendxl.client;

import org.bouncycastle.openssl.PEMReader;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SslUtil
{
	public static final String PP_ALGORITHM = "RSA";
	public static final String DEFAULT_PASSWORD = "password";
	
	static
	{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	public static SSLSocketFactory getSocketFactory(final File caCrtFile, final File crtFile, final File keyFile)
		throws GeneralSecurityException, IOException
	{
		try (
			InputStream caCrtInputStream = new FileInputStream(caCrtFile);
			InputStream crtInputStream = new FileInputStream(crtFile);
			InputStream keyInputStream = new FileInputStream(keyFile))
		{
			return getSocketFactory(caCrtInputStream, crtInputStream, keyInputStream);
		}
	}
	
	public static SSLSocketFactory getSocketFactory(final byte[] caCrt, final byte[] crt, final byte[] key)
		throws GeneralSecurityException, IOException
	{
		try (
			InputStream caCrtInputStream = new ByteArrayInputStream(caCrt);
			InputStream crtInputStream = new ByteArrayInputStream(crt);
			InputStream keyInputStream = new ByteArrayInputStream(key))
		{
			return getSocketFactory(caCrtInputStream, crtInputStream, keyInputStream);
		}
	}
	
	public static SSLSocketFactory getSocketFactory(final InputStream caCrtInputStream,
		final InputStream crtInputStream, final InputStream keyInputStream)
		throws GeneralSecurityException, IOException
	{
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		
		X509Certificate caCert;
		X509Certificate cert;
		
		//load CA certificate
		caCert = (X509Certificate) factory.generateCertificate(caCrtInputStream);
		
		//load client certificate
		cert = (X509Certificate) factory.generateCertificate(crtInputStream);
		
		//CA certificate is used to authenticate server
		KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
		caKs.load(null, null);
		caKs.setCertificateEntry("ca-certificate", caCert);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(caKs);
		
		//client key and certificates are sent to server so it can authenticate us
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null, null);
		ks.setCertificateEntry("certificate", cert);
		ks.setKeyEntry("private-key", parsePrivateKey(keyInputStream), DEFAULT_PASSWORD.toCharArray(),
			new java.security.cert.Certificate[] { cert });
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, DEFAULT_PASSWORD.toCharArray());
		
		//create SSL socket factory
		SSLContext context = SSLContext.getInstance("TLSv1.2");
		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		
		return context.getSocketFactory();
	}
	
	private static PrivateKey parsePrivateKey(final InputStream inputStream) throws
		GeneralSecurityException, IOException
	{
		try (PEMReader parser = new PEMReader(new InputStreamReader(inputStream)))
		{
			//read the object from the pem reader
			Object pemObject = parser.readObject();
			
			//check to see if this object is a keypair
			if (pemObject instanceof KeyPair)
			{
				KeyPair caKeyPair = (KeyPair) pemObject;
				return caKeyPair.getPrivate();
			}
			else
			{
				return (PrivateKey) pemObject;
			}
		}
	}
}