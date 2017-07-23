package com.akivamu.gdx;

import com.akivamu.gdx.crypto.Crypto;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.File;

public class TextureDecryptor {
    public static Texture loadInternalTexture(Crypto crypto, String internalPath) {
        return loadTexture(crypto, Gdx.files.internal(internalPath));
    }

    // For desktop
    public static Texture loadTexture(Crypto crypto, String filePath) {
        return loadTexture(crypto, new FileHandle(filePath));
    }

    // For desktop
    public static Texture loadTexture(Crypto crypto, File file) {
        return loadTexture(crypto, new FileHandle(file));
    }

    public static Texture loadTexture(Crypto crypto, FileHandle file) {
        return loadTexture(crypto, file, false);
    }

    public static Texture loadTexture(Crypto crypto, FileHandle file, boolean useMipMaps) {
        return loadTexture(crypto, file, null, useMipMaps);
    }

    public static Texture loadTexture(Crypto crypto, FileHandle file, Pixmap.Format format, boolean useMipMaps) {
        try {
            byte[] bytes = file.readBytes();
            if (crypto != null) {
                bytes = crypto.decrypt(bytes, true);
            }
            Pixmap pixmap = new Pixmap(new Gdx2DPixmap(bytes, 0, bytes.length, 0));
            return new Texture(pixmap, format, useMipMaps);
        } catch (Exception e) {
            throw new GdxRuntimeException("Couldn't load file: " + file, e);
        }
    }
}
