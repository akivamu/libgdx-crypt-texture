package com.akivamu.gdx.crypto;

public interface Crypto {
    byte[] encrypt(byte[] bytes, boolean inPlace);

    byte[] decrypt(byte[] bytes, boolean inPlace);

    byte encrypt(byte val);

    byte decrypt(byte val);
}
