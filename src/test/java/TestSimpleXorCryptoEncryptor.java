import com.akivamu.gdx.exe.SimpleXorCryptoEncryptor;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestSimpleXorCryptoEncryptor {
    @Test
    public void testPrintHelp() {
        SimpleXorCryptoEncryptor.main(new String[]{
                "-h"
        });
    }

    @Test
    public void testInvalidParamsMissing() {
        SimpleXorCryptoEncryptor.main(new String[]{
                "badlogic.jpg"
        });
    }

    @Test
    public void testInvalidParamsInvalidKey() {
        SimpleXorCryptoEncryptor.main(new String[]{
                "1234",
                "badlogic.jpg",
        });
    }

    @Test
    public void testSingleFile() {
        SimpleXorCryptoEncryptor.main(new String[]{
                "123",
                "badlogic.jpg",
        });
    }

    @Test
    public void testSingleFileWithOutputParam() {
        SimpleXorCryptoEncryptor.main(new String[]{
                "123",
                "badlogic.jpg",
                "output",
        });
    }

    @Test
    public void testFolder() {
        SimpleXorCryptoEncryptor.main(new String[]{
                "123",
                ".",
        });
    }

    @Test
    public void testFolderWithOutputParam() {
        SimpleXorCryptoEncryptor.main(new String[]{
                "123",
                ".",
                "output",
        });
    }
}
