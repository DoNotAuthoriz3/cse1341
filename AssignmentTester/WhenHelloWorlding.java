import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by the.machine on 10/6/2015.
 */
public class WhenHelloWorlding extends BaseTest implements JUnitTest
{
    @Test
    public void printHelloWorld()
    {
        System.out.println("Hello World");
        assertTrue(true);
    }
}
