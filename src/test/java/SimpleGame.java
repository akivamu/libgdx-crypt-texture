import com.akivamu.gdx.TextureDecryptor;
import com.akivamu.gdx.TextureEncryptor;
import com.akivamu.gdx.crypto.Crypto;
import com.akivamu.gdx.crypto.SimpleXorCrypto;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimpleGame extends ApplicationAdapter {
    SpriteBatch batch;

    Crypto crypto = new SimpleXorCrypto((byte) 123);

    static final int originW = 256;
    static final int originH = 256;
    String originFilePath = "badlogic.jpg";
    String encryptedFilePath = "badlogic.tex";
    Texture originTexture;
    Texture originTextureByDecryptor;
    Texture decryptedTexture;
    BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.setColor(0, 0, 1, 1);

        originTexture = new Texture(originFilePath);

        originTextureByDecryptor = TextureDecryptor.loadTexture(null, originFilePath);

        TextureEncryptor.encryptToFile(crypto, originFilePath, encryptedFilePath);

        decryptedTexture = TextureDecryptor.loadTexture(crypto, encryptedFilePath);

        System.out.println("isEqual: " + isEqual(originTexture, decryptedTexture));
    }

    private boolean isEqual(Texture a, Texture b) {
        if (a.getDepth() != b.getDepth()) return false;
        if (a.getHeight() != b.getHeight()) return false;
        if (a.getWidth() != b.getWidth()) return false;

        if (!a.getTextureData().getFormat().equals(b.getTextureData().getFormat())) return false;
        if (a.getTextureData().getHeight() != b.getTextureData().getHeight()) return false;
        if (a.getTextureData().getWidth() != b.getTextureData().getWidth()) return false;
        if (!a.getTextureData().getType().equals(b.getTextureData().getType())) return false;

        return true;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        int x = 1;
        font.draw(batch, "Origin", x, originH + 20);
        batch.draw(originTexture, x, 1);

        x += originW + 1;
        font.draw(batch, "non encrypt", x, originH + 20);
        batch.draw(originTextureByDecryptor, x, 1);

        x += originW + 1;
        font.draw(batch, "decrypt", x, originH + 20);
        batch.draw(decryptedTexture, x, 1);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        originTexture.dispose();
        originTextureByDecryptor.dispose();
        decryptedTexture.dispose();
        FileHandle fileHandle = new FileHandle(encryptedFilePath);
        fileHandle.delete();
    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.width = originW * 3 + 4;
        configuration.height = originH + 2 + 50;
        new LwjglApplication(new SimpleGame(), configuration);
    }
}