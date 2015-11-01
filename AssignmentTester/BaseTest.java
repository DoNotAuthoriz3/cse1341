import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Created by canon on 9/8/2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class BaseTest
{
    public InputStream input = null;
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
}