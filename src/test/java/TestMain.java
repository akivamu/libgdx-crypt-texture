import com.akivamu.gdx.Main;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestMain {
    @Test
    public void testPrintHelp() {
        Main.main(new String[]{
                "-h"
        });

        Main.main(new String[]{
                "badlogic.jpg"
        });

        Main.main(new String[]{
                "1234",
                "badlogic.jpg",
        });
    }

    @Test
    public void testSingleFile() {
        Main.main(new String[]{
                "123",
                "badlogic.jpg",
        });

        Main.main(new String[]{
                "123",
                "badlogic.jpg",
                "output",
        });
    }

    @Test
    public void testFolder() {
        Main.main(new String[]{
                "123",
                ".",
        });

        Main.main(new String[]{
                "123",
                ".",
                "output",
        });
    }
}
