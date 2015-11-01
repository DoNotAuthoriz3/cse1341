import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Created by canon on 9/8/2015.
 */
public abstract class BaseTest
{
    public InputStream in = null;
    InputStream origIn  = System.in;

    public ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream origOut = System.out;

    @Before
    public void setUp()
    {
        System.setOut(new PrintStream((out)));
    }

    @After
    public void setDown()
    {
        System.setIn(origIn);
        System.setOut(origOut);
    }

    public class MyRun implements Runnable
    {
        public void setIn(InputStream in) { System.setIn(in); }
        public void setOut(ByteArrayOutputStream out) { System.setOut(new PrintStream(out)); }

        public void run() {
            Example.main(new String[0]); }
    }
}