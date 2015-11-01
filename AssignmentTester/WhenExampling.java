import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.InterruptedException;
import java.lang.Runnable;
import java.lang.String;
import java.lang.System;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Created by the.machine on 10/6/2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WhenExampling extends BaseTest implements JUnitTest {
    @Test
    public void testReturnTrue()
    {
        System.err.println("Testing Example.returnTrue()");
        assertTrue("Example.returnTrue() did not return true!", Example.returnTrue());
    }

    @Test
    public void testNotReturnFalse()
    {
        System.err.println("Testing false == !Example.returnTrue()");
        assertFalse("!Example.returnTrue was not false!", !Example.returnTrue());
    }

    @Test
    public void testReturnTrueANDReturnTrue()
    {
        System.err.println("Testing false == (Example.returnTrue() && Example.returnTrue())");
        assertFalse("Example.returnTrue() and Example.returnTrue() is not false!", Example.returnTrue() && Example.returnTrue());
    }

    @Test
    public void testMun()
    {
        System.err.println("Testing main reads and prints name");

        // This is how you pass strings to the program you are test
        in = new ByteArrayInputStream("Aaron\n".getBytes());
        System.setIn(in);
        // each time you must set input = a new Byte... and then System.setIn on the new object
        Example.mun(new String[0]);

        String textToVerify = "Aaron";
        String x = waitForText(out, textToVerify, 30000);
        assertTrue("Did not find output", x.contains(textToVerify));

        /* this is entirely for debugging to see what the output is. Note that I use System.err, not System.out;
        System.out is being redirected so you would not see it on the console. System.err is not. This is very hacky
        but makes debugging quite a lot easier. If you don't need to see what the output is (i.e. you finished
        the test and it works) you don't need to use a helper variable. Instead you could just call
        assertTrue("Did not find output", waitForText(out, textToVerify, 30000).contains(textToVerify)); */
        System.err.println("program output is: " + x);
    }

    @Test
    public void testMuhn()
    {
        System.err.println("Testing muhn reads and prints out name and age");
        String output = "";

        // This is how you pass strings to the program you are test
        in = new ByteArrayInputStream("Aaron\n27\n".getBytes());
        System.setIn(in);
        // each time you must set input = a new Byte... and then System.setIn on the new object

        /* This lovely piece of Java garbage-magic is an anonymous class that implements the Runnable interface.
        This is how you make the main method of the application being tested *go*. This isn't technically necessary;
        it's possibly to just supply all input for the program in one big byte array at the beginning, then let the
        program run and sort out the necessary outputs along the way. But this better mimics a user interacting with
        the program. */
        MyRunRun muhn = new MyRunRun();
        muhn.setOut(out);
        muhn.run();

        String textToVerify = "Aaron";
        String x = waitForText(out, textToVerify, 30000);
        assertTrue("Did not find output", x.contains(textToVerify));
        System.err.println(output += x);

        in = new ByteArrayInputStream("27\n".getBytes());
        muhn.setIn(in);
        textToVerify = "27";
        x = waitForText(out, textToVerify, 30000);
        assertTrue("Did not find output", x.contains(textToVerify));
        System.err.println(output += x);

        /* this is entirely for debugging to see what the output is. Note that I use System.err, not System.out;
        System.out is being redirected so you would not see it on the console. System.err is not. This is very hacky
        but makes debugging quite a lot easier. If you don't need to see what the output is (i.e. you finished
        the test and it works) you don't need to use a helper variable. Instead you could just call
        assertTrue("Did not find output", waitForText(out, textToVerify, 30000).contains(textToVerify)); */
        System.err.println("program output is: " + output);
    }

    /* waits for the input string for up to 30000 seconds; returns true if the input string is found in the System.out
    stream in that time frame. */
    private boolean waitForText(ByteArrayOutputStream out, String textToVerify) {
        return waitForText(out, textToVerify, 30000).contains(textToVerify);
    }

    private String waitForText(ByteArrayOutputStream out, String textToVerify, long timeToWait) {
        String currentOut = "";
        long start = System.currentTimeMillis();
        while (!currentOut.contains(textToVerify) && (System.currentTimeMillis() - start < timeToWait)) {
            currentOut += out.toString();
        }
        return currentOut;
    }

    private class MyRunRun implements Runnable
    {
        public void setIn(InputStream in) { System.setIn(in); }
        public void setOut(ByteArrayOutputStream out) { System.setOut(new PrintStream(out)); }

        public void run() { Example.muhn(new String[0]); }
    }
}
