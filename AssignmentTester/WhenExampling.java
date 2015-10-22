import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by the.machine on 10/6/2015.
 */
public class WhenExampling implements JUnitTest
{
    @Test
    public void printHelloWorld()
    {
        System.out.println("Testing Example.returnTrue()");
        assertTrue(Example.returnTrue());
    }
}
