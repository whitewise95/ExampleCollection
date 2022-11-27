package com.example.examplecollection.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Aes256Utils {

	private static final String transformation = "AES/CBC/PKCS5Padding";
	private static final String key = "gytjdwjdrlx/*?D(^+Kxqothd!2zlae2";
	private static final String iv = "gytjdq3?/w2y$B&E"; // 16byte

	public static String encrypt(String text) throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);

		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

		return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
	}

	public static String decrypt(String text) throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);

		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());

		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

		return new String(cipher.doFinal(Base64.getDecoder().decode(text)), StandardCharsets.UTF_8);
	}
}
