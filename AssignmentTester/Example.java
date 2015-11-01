import java.util.Scanner;

public class Example
{
    public static boolean returnTrue () { return true; }

    public static void mun (String... args)
    {
        System.out.print("Hello. What is your name? ");
        String nomen = new Scanner(System.in).nextLine();
        System.out.println("Hullo " + nomen);
    }

    public static void muhn (String... args)
    {
        System.out.print("Hello. What is your name? ");
        System.err.print("Hello. What is your name? ");
        Scanner input = new Scanner(System.in);
        String nomen = input.nextLine();
        System.out.println("Hullo " + nomen);
        System.err.println("Hullo " + nomen);

        System.out.print("And how old are you? ");
        System.err.print("And how old are you? ");
        String age = input.nextLine();
        System.out.println("You are " + age + " years old!");
        System.err.println("You are " + age + " years old!");
    }
}
