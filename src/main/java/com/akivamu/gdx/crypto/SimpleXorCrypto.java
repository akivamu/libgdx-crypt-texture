package com.akivamu.gdx.crypto;

public class SimpleXorCrypto implements Crypto {
    private final byte number;

    public SimpleXorCrypto(byte number) {
        this.number = number;
    }

    @Override
    public byte[] encrypt(byte[] input, boolean inPlace) {
        if (input == null) throw new IllegalArgumentException("Byte array must not null");

        byte[] result = inPlace ? input : new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            result[i] = (byte) (input[i] ^ number);
        }

        return result;
    }

    @Override
    public byte[] decrypt(byte[] encryptedBytes, boolean inPlace) {
        return encrypt(encryptedBytes, inPlace);
    }

    @Override
    public byte encrypt(byte val) {
        return (byte) (val ^ number);
    }

    @Override
    public byte decrypt(byte val) {
        return (byte) (val ^ number);
    }
}
