import java.util.ArrayList;

/**
 * The GitSwitcher class extends the AssignmentTester functionality to also go through a list of student GitHub repos
 * and grade the input assignments for each student. It will store the grade in the appropriate column in the supplied
 * CSV file.
 *
 * Created by dfischer on 9/8/2015.
 */
public class GitSwitcher
{
   public static void main (String... args)
   {
      System.out.println("Running GetSwitcher as a standalone. Testing...\n");

      ArrayList<String> students = new ArrayList<String>();
      // TODO: get the list of student github accounts from *somewhere*

      for (String stu : students)
      {
         // TODO: change to the student's repo so the tests will run against his/her code

         AssignmentTester.testPrograms(args);

         // TODO: put the grades in the downloaded, supplied CSV file for blackboard
      }
   }
}
