package com.akivamu.gdx.exe;

import com.akivamu.gdx.crypto.Crypto;
import com.akivamu.gdx.crypto.SimpleXorCrypto;

public class SimpleXorCryptoEncryptor extends Main {

    protected SimpleXorCryptoEncryptor(String[] args) throws IllegalArgumentException {
        super(args);
    }

    @Override
    protected Crypto buildCrypto(String key) throws IllegalArgumentException {
        try {
            byte xorNum = Byte.parseByte(key);
            return new SimpleXorCrypto(xorNum);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid key. Must be a number from 0-255");
        }
    }

    public static void main(String[] args) {
        try {
            SimpleXorCryptoEncryptor instance = new SimpleXorCryptoEncryptor(args);
            instance.doEncrypt();
        } catch (IllegalArgumentException e) {
            onError(e.getMessage());
        }
    }

    // Make public static API for other usage
    public static void process(String key, String inputPath, String outputDirName) {
        try {
            SimpleXorCryptoEncryptor instance = new SimpleXorCryptoEncryptor(new String[]{key, inputPath, outputDirName});
            // Encrypt
            instance.doEncrypt();
        } catch (IllegalArgumentException e) {
            onError(e.getMessage());
        }
    }

    @Override
    protected void printHelp() {
        System.out.println("Encrypt file by simple xor crypto. Key must be number from 0-255");
        super.printHelp();
    }
}
