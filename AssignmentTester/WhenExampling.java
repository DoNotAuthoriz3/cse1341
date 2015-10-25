import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by the.machine on 10/6/2015.
 */
public class WhenExampling implements JUnitTest
{
    @Test
    public void testReturnTrue()
    {
        System.out.println("Testing Example.returnTrue()");
        assertTrue(Example.returnTrue());
    }

    @Test
    public void testNotReturnFalse()
    {
        System.out.println("Testing false == !Example.returnTrue()");
        assertFalse(!Example.returnTrue());
    }

    @Test
    public void testReturnTrueANDReturnTrue()
    {
        System.out.println("Testing false == (Example.returnTrue() && Example.returnTrue())");
        assertFalse(Example.returnTrue() && Example.returnTrue());
    }
}
