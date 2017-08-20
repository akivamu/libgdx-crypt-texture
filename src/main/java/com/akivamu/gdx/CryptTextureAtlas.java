package com.akivamu.gdx;

import com.akivamu.gdx.crypto.Crypto;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;

public class CryptTextureAtlas extends TextureAtlas {
    private final Crypto crypto;

    // Override constructor to load encrypt texture
    public CryptTextureAtlas(Crypto crypto, String encryptedPackFilePath) {
        this(crypto, new CryptFileHandle(crypto, encryptedPackFilePath));
    }

    public CryptTextureAtlas(Crypto crypto, FileHandle packFile) {
        this(crypto, packFile, packFile.parent());
    }

    public CryptTextureAtlas(Crypto crypto, FileHandle packFile, boolean flip) {
        this(crypto, packFile, packFile.parent(), flip);
    }

    public CryptTextureAtlas(Crypto crypto, FileHandle packFile, FileHandle imagesDir) {
        this(crypto, packFile, imagesDir, false);
    }

    public CryptTextureAtlas(Crypto crypto, FileHandle packFile, FileHandle imagesDir, boolean flip) {
        this(crypto, new TextureAtlasData(packFile, imagesDir, flip));
    }

    public CryptTextureAtlas(Crypto crypto, TextureAtlasData data) {
        this.crypto = crypto;
        if (data != null) load(data);
    }

    private void load(TextureAtlasData data) {
        ObjectMap<TextureAtlasData.Page, Texture> pageToTexture = new ObjectMap<TextureAtlasData.Page, Texture>();
        for (TextureAtlasData.Page page : data.getPages()) {
            Texture texture = TextureDecryptor.loadTexture(crypto, page.textureFile, page.format, page.useMipMaps);
            texture.setFilter(page.minFilter, page.magFilter);
            texture.setWrap(page.uWrap, page.vWrap);
            getTextures().add(texture);
            pageToTexture.put(page, texture);
        }

        // Same as libGDX source
        for (TextureAtlasData.Region region : data.getRegions()) {
            int width = region.width;
            int height = region.height;
            AtlasRegion atlasRegion = new AtlasRegion(pageToTexture.get(region.page), region.left, region.top,
                    region.rotate ? height : width, region.rotate ? width : height);
            atlasRegion.index = region.index;
            atlasRegion.name = region.name;
            atlasRegion.offsetX = region.offsetX;
            atlasRegion.offsetY = region.offsetY;
            atlasRegion.originalHeight = region.originalHeight;
            atlasRegion.originalWidth = region.originalWidth;
            atlasRegion.rotate = region.rotate;
            atlasRegion.splits = region.splits;
            atlasRegion.pads = region.pads;
            if (region.flip) atlasRegion.flip(false, true);
            getRegions().add(atlasRegion);
        }
    }
}

