package com.akivamu.gdx;

import com.akivamu.gdx.crypto.Crypto;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CryptSkin extends Skin {
    public CryptSkin(FileHandle skinFile, Crypto crypto) {
        this(skinFile, new CryptTextureAtlas(skinFile.sibling(skinFile.nameWithoutExtension() + ".atlas"), crypto));
        load(skinFile);
    }

    public CryptSkin(FileHandle skinFile, TextureAtlas atlas) {
        super(atlas);
        load(skinFile);
    }

    public CryptSkin(TextureAtlas atlas) {
        super(atlas);
    }
}
