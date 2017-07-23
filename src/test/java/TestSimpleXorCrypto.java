import com.akivamu.gdx.crypto.Crypto;
import com.akivamu.gdx.crypto.SimpleXorCrypto;
import com.badlogic.gdx.math.MathUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestSimpleXorCrypto {
    private static final Crypto crypto = new SimpleXorCrypto((byte) 123);

    private byte[] generateRandomBytes(int length) {
        byte[] ret = new byte[length];
        for (int i = 0; i < length; i++) {
            ret[i] = (byte) MathUtils.random(Byte.MIN_VALUE, Byte.MAX_VALUE);
        }
        return ret;
    }

    private boolean isEqual(byte[] a, byte[] b) {
        if (a.length != b.length) return false;

        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }

    @Test
    public void testCryptByteArray() {
        byte[] originBytes = generateRandomBytes(10);

        byte[] encryptedBytes = crypto.encrypt(originBytes, false);
        Assert.assertFalse(isEqual(encryptedBytes, originBytes));

        byte[] decryptedBytes = crypto.decrypt(encryptedBytes, false);
        Assert.assertTrue(isEqual(decryptedBytes, originBytes));
    }
}
