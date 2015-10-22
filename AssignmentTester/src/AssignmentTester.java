import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

//import org.junit.runner.JUnitCore;
//import org.junit.runner.Result;

/**
 * Created by dfischer on 9/8/2015.
 */
public class AssignmentTester
{
   private static final String LookupTable = "TestsForAssignmentLookupTable.xml";

   /**
    * AssignmentTest can be run as a standalone program. If passed the name of a Java file (or files) as an argument,
    * it will run whatever tests are appropriate for that file according to the accompanying
    * TestsForAssignmentLookupTable.xml file.
    *
    * @param args
    */
   public static void main (String... args)
   {
      //This program only makes sense if there is at least one file to test
      if (0 == args.length)
      {
         System.out.println("Usage: java AssignmentTest <fileToTest> OR " +
               "java AssignmentTest <fileToTest> <fileToTest>...");
         System.out.println("\ni.e. java AssignmentTest Hello.java");
         System.out.println("or i.e. java AssignmentTest Hello.java Age.java SimpleCalculator.java Next.java");
      }

      System.out.println("Entering for loop");
      // test all of the programs passed in via command line arguments
      for (String argument : args)
      {
         try
         {
            System.out.println("Running test for " + argument);
            runAllTestsFor(argument);
         } catch (Exception ex)
         {
            System.out.println("Error: failed while attempting to test " + argument);
            ex.printStackTrace();
         }
      }
   }

   /**
    * Will run all of the JUnit tests corresponding to the input file. Determines which JUnit tests to run via lookup
    * in the TestsForAssignmentLookupTable.xml file. Throws a bevy of exceptions associated with not being able to
    * build a JUnitTest object from the class name given in the XML file.
    *
    * @param argument
    */
   public static void runAllTestsFor (String argument) throws NoSuchMethodException, IllegalAccessException,
         InvocationTargetException, InstantiationException
   {
      try
      {
         // all Java source code files must have a .java suffix
         if (!argument.endsWith(".java"))
         {
            String[] message = {"Incorrect file suffix; expected *.java, found: " + argument};
            throw new InvalidArgumentException(message);
         }

         // verify that the program compiles
         JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
         String currentDirectory;
         currentDirectory = System.getProperty("user.dir");
         int compilationResult = compiler.run(null, null, null, currentDirectory + File.separator + argument);
         System.out.println("Compile result code = " + compilationResult);

         String[] className = argument.split("\\.java");

         System.out.println("className[0] is " + className[0]);

         // run the tests
         Class<JUnitTest> testClass = getNameOfTestClassFor(argument);
         JUnitCore junit = new JUnitCore();
         // why can run not be overloaded to take a single class? WHY?!
         Result testResult = junit.run( new Class<?>[] {testClass} );
         System.out.println("Tests succeeded: " + testResult.wasSuccessful());
      }
      // At this point, regardless of the exception type the outcome is the same, so might as well just catch the
      // superclass.
      catch (Exception ex)
      {
         System.out.println("Error testing " + argument);
         ex.printStackTrace();
      }
   }

   /**
    * Uses the TestsForAssignmentLookupTable.xml file to determine which JUnitTest to run on the input file. This
    * method expects each source code file to be tested by a single JUnitTest class in a 1:1 manner. Support for more
    * is unnecessary at the time of this writing, and in any case would necessarily cause this method to abort or
    * return only the first (as it returns a single Class<JUnitTest> object, not any kind of array or List.
    *
    * @param argument
    * @return
    */
   private static Class<JUnitTest> getNameOfTestClassFor (String argument) throws TestNotFoundException
   {
      try
      {
         // all of these steps are really necessary to get the XML loaded and parsed
         File testList = new File("res/" + LookupTable);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(testList);
         doc.getDocumentElement().normalize();
         NodeList testableSourceCodeFiles = doc.getElementsByTagName("file_test_mapping");

         // iterate over all of the testable files in the XML files
         for (int i = 0; i < testableSourceCodeFiles.getLength(); i++)
         {
            Node curElement = testableSourceCodeFiles.item(i);

            /*
             * test each element in the file against our input argument until we find a match; this is a terrible,
              * terrible method of finding the match, and I should look up the proper XML stuff API and fix it as
              * soon as I can possibly get un-lazy.
             */
            if (curElement.getNodeType() == Node.ELEMENT_NODE)
            {
               Element element = (Element) curElement;
               String fileName = element.getAttribute("source_code_file_name");

               // ick
               if (fileName.equals(argument))
               {
                  // this is such a horrifyingly fragile design even I don't understand why I don't bother to do better
                  // oh wait yes I do it's because the XML Java API IS TERRIBLE and I haven't seen a betterer one.
                  // WHY DOES JAVA SUCK?!
                  String testName = element.getChildNodes().item(1).getTextContent();

                  String currentDirectory;
                  currentDirectory = System.getProperty("user.dir") + File.separator;

                  // this is absoluteyl awful workaround to get a properly formatted URL for a string containing a file path.
                  URL[] urls = { new File(currentDirectory).toURI().toURL() };
                  URLClassLoader urlcl = new URLClassLoader(urls);

                  // yay reflection (finally)
                  Class<JUnitTest> testClass;
                  testClass = (Class<JUnitTest>) urlcl.loadClass(testName);

                  return testClass;
               }
            }
         }
      }
      // lazily handling this for now. TODO: fixit.
      catch (Exception e) { e.printStackTrace(); }

      // if we got here then we failed to find the correct test, one way or another.
      throw new TestNotFoundException();
   }

   // TODO: make this descriptive
   private static class TestNotFoundException extends ClassNotFoundException {}
}

