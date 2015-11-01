import java.util.Scanner;

public class Example
{
    public static boolean returnTrue () { return true; }

    public static void main (String... args)
    {
        System.out.print("Hello. What is your name? ");
        String nomen = new Scanner(System.in).nextLine();
        System.out.println("Hullo " + nomen);
    }
}
