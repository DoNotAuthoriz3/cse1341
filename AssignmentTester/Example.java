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
        Scanner input = new Scanner(System.in);
        String nomen = input.nextLine();
        System.out.println("Hullo " + nomen);

        System.out.print("And how old are you? ");
        String age = input.nextLine();
        System.out.println("You are " + age + " years old!");
    }

    public static void main (String... args)
    {
        System.out.print("Hello. What is your name? ");
        Scanner input = new Scanner(System.in);
        String nomen = input.nextLine();
        System.out.println("Hullo " + nomen);

        System.out.print("And how old are you? ");
        String age = input.nextLine();
        System.out.println("You are " + age + " years old!");


        System.out.print("And are you male/femal? ");
        String gender = input.nextLine();
        System.out.println("You are " + gender);
    }
}
