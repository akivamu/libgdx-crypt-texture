package com.akivamu.gdx;

import com.akivamu.gdx.crypto.Crypto;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;
import java.io.InputStream;

class CryptFileHandle extends FileHandle {
    private final Crypto crypto;

    CryptFileHandle(Crypto crypto, String fileName) {
        super(fileName);
        this.crypto = crypto;
    }

    @Override
    public InputStream read() {
        final InputStream is = super.read();

        return new InputStream() {
            @Override
            public int read() throws IOException {
                int val = is.read();
                if (val == -1) return -1;
                return crypto.decrypt((byte) val);
            }
        };
    }
}
