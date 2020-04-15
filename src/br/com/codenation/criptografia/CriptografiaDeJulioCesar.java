package br.com.codenation.criptografia;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

public class CriptografiaDeJulioCesar {
	
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	
	public static void main(String...args) {
		int shifts = 8;
		String encrypted = "vwjwlg mfxmkba bpm axivqap qvycqaqbqwv. uwvbg xgbpwv";
		String answer = decrypt(shifts, encrypted);
		System.out.println(answer);
		System.out.println(getSHA1(answer));
	}

	private static String decrypt(int jumps, String encrypted) {
		int i = 0;
		StringBuilder builder = new StringBuilder();
		while (i < encrypted.length()) {
			char encryptedChar = encrypted.charAt(i);
			int idxAtAlphabet = ALPHABET.indexOf(String.valueOf(encryptedChar).toLowerCase());
			if (idxAtAlphabet == -1) {
				builder.append(encryptedChar);
				i++;
				continue;
			}
			int indexAfterJumps = (idxAtAlphabet - jumps) % ALPHABET.length();
			int rawIndex = indexAfterJumps < 0 ? ALPHABET.length() + indexAfterJumps : indexAfterJumps;
			
			char decryptedChar = ALPHABET.charAt(rawIndex);
			builder.append(decryptedChar);
			i++;
		}
		return builder.toString();
	}

	private static String getSHA1(String decrypted) {
		MessageDigest digest;
		final String algorithm = "SHA-1";
		final String charSet = "utf8";
		try {
			digest = MessageDigest.getInstance(algorithm);
			digest.reset();
			digest.update(decrypted.getBytes(charSet));
			return String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchElementException(algorithm);
		} catch (UnsupportedEncodingException e) {
			throw new NoSuchElementException(charSet);
		}
	}

}
