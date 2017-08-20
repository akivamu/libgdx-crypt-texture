# libgdx-crypt-texture
A simple library to encrypt and decrypt files (include libGdx Texture)

# Usage
Step 1. Use other tools to create texture file (e.g. [TexturePacker](https://github.com/crashinvaders/gdx-texture-packer-gui))  
Step 2. Encrypt generated texture files by `libgdx-crypt-texture` executable jar.  
Step 3. In libGdx project source code:
  - If you use `Texture`, decrypting by `TextureDecryptor`
  - If you want to load texture to skin, use `CryptTextureAtlas` to create skin

## Encryption
The class `TextureEncryptor` should be used in dev PC only.  
You can build this project into executable jar file, then run on command line to encrypt files.  
Command line arguments:
  - key (required) : key to encrypt (0-255)
  - input (required) : input file/folder
  - output (optional) : output folder
This executable currently uses `SimpleXorCrypto` algorithm.

## Decryption
Use class `TextureDecryptor` to decrypt file, passing your specific crypto implementation.  
```
// Your secret key to encrypt
int secretKey = 123;

// Use simple crypto implementation
Crypto crypto = new SimpleXorCrypto((byte) secretKey);

// Decrypt texture file
Texture decrypted = TextureDecryptor.loadTexture(crypto, "encrypted_texture_file.png");
```

## Crypto
Make your custom encrypting algorithm by implementing this interface.  
```
public interface Crypto {
    byte[] encrypt(byte[] bytes, boolean inPlace);

    byte[] decrypt(byte[] bytes, boolean inPlace);
}
```
The library provides a sample algorithm implementation called `SimpleXorCrypto`.

## TextureAtlas and Skin
LibGdx `Skin` uses texture atlas to manage textures, texture regions, drawable...  
The class `CryptTextureAtlas` extends libGdx's class `TextureAtlas`, adding crypto function  
We use it when initialize `Skin`  
```
// An example crypto
int secretKey = 123;
Crypto crypto = new SimpleXorCrypto((byte) secretKey);

// Decrypt texture file into texture atlas
CryptTextureAtlas atlas = new CryptTextureAtlas(crypto, "yourAtlas.pack");

// Load into skin
Skin skin = new Skin(yourSkinFile, atlas);
```

# Build system
The project use gradle with java plugin, everything is as normal.
To build executable jar, run shadowJar:
```
gradlew shadowJar
```
Output jar is in ./build/libs folder
