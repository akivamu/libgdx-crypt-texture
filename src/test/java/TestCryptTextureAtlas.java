import com.akivamu.gdx.CryptTextureAtlas;
import com.akivamu.gdx.crypto.Crypto;
import com.akivamu.gdx.crypto.SimpleXorCrypto;
import com.akivamu.gdx.exe.SimpleXorCryptoEncryptor;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TestCryptTextureAtlas extends ApplicationAdapter {
    private SpriteBatch batch;

    private Crypto crypto = new SimpleXorCrypto((byte) 123);

    private static final int originW = 256;
    private static final int originH = 256;
    private static final String ORIGIN_PATH = "atlas";
    private static final String ENCRYPTED_PATH = "encryptedAtlas";

    private TextureRegion originTextureRegion;
    private TextureRegion decryptTextureRegion;
    private BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.setColor(0, 0, 1, 1);

        // Origin
        TextureAtlas originTextureAtlas = new TextureAtlas(ORIGIN_PATH + "/test.atlas");
        Skin originSkin = new Skin(originTextureAtlas);
        originTextureRegion = originSkin.getRegion("badlogic");

        // Encrypt
        SimpleXorCryptoEncryptor.process("123", "atlas", "encryptedAtlas");

        // Decrypt
        CryptTextureAtlas cryptTextureAtlas = new CryptTextureAtlas(crypto, ENCRYPTED_PATH + "/test.atlas");
        Skin skin = new Skin(cryptTextureAtlas);
        decryptTextureRegion = skin.getRegion("badlogic");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        int x = 1;
        font.draw(batch, "Origin", x, originH + 20);
        batch.draw(originTextureRegion, x, 1);

        x += originW + 1;
        font.draw(batch, "decrypt", x, originH + 20);
        batch.draw(decryptTextureRegion, x, 1);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        FileHandle fileHandle = new FileHandle(ENCRYPTED_PATH);
        fileHandle.deleteDirectory();
    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.width = originW * 3 + 4;
        configuration.height = originH + 2 + 50;
        new LwjglApplication(new TestCryptTextureAtlas(), configuration);
    }
}