# libgdx-crypt-texture
A simple library to encrypt and decrypt files (include libGdx Texture)

# Usage
## Encryption
The class `TextureEncryptor` should be used in dev PC only.  
You can build this project into executable jar file, then run on command line to encrypt files.  
Command line arguments:
  - <key>       (required) key to encrypt (0-255)
  - <input>     (required) input file/folder
  - [output]    (optional) output folder
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

## Skin and TextureAtlas
The class `CryptTextureAtlas` extends libGdx's class `TextureAtlas`. Texture atlas will be used when create `Skin`.  
The class `CryptSkin` extends libGdx's class `Skin`, and utilizes `CryptTextureAtlas` to store decrypt texture. Skin
will be used to get texture regions from texture atlas.  
```
// Your secret key to encrypt
int secretKey = 123;

// Use simple crypto implementation
Crypto crypto = new SimpleXorCrypto((byte) secretKey);

// Your skin file
FileHandle yourSkinFile = Gdx.files.internal("yourGameFile.skin");

// Decrypt texture file
Skin skin = new CryptSkin(yourSkinFile, crypto);
```

# Build system
The project use gradle with java plugin, everything is as normal.
To build executable jar, run shadowJar:
```
gradlew shadowJar
```
Output jar is in ./build/libs folder
