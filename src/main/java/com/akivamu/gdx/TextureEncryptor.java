package com.akivamu.gdx;

import com.akivamu.gdx.crypto.Crypto;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

/**
 * This class should be use on PC, to make encrypted texture file
 */
public class TextureEncryptor {
    public static void encryptToFile(Crypto crypto, String inputFilePath, String outputFilePath) {
        encryptToFile(crypto, new File(inputFilePath), new File(outputFilePath));
    }

    public static void encryptToFile(Crypto crypto, File inputFile, File outputFile) {
        FileHandle inputFileHandle = new FileHandle(inputFile);
        byte[] bytes = inputFileHandle.readBytes();
        crypto.decrypt(bytes, true);

        FileHandle outputFileHandle = new FileHandle(outputFile);
        outputFileHandle.writeBytes(bytes, false);
    }
}
