import java.util.NoSuchElementException;
import java.util.Scanner;

public class Input {
    public static int getInt(Scanner console, int min) {
        int input = 0;
        boolean isInt = false;
        do {
            try {
                input = Integer.parseInt(console.nextLine());
                isInt = input >= min;
            } catch (NumberFormatException e) {
                System.out.println("That's not an Integer");
                isInt = false;
            } catch (NoSuchElementException e) {
                System.out.printf("Scanner error: no element found%nNoSuchElementException%n");
                isInt = false;
            } catch (IllegalStateException e) {
                System.out.printf("Scanner error: scanner closed%IllegalStateException%n");
                isInt = false;
            }
            if (!isInt) {System.out.printf("Enter an Integer greater than %s%n", min-1);};
        } while (!isInt);
        return input;
    }

    public static int getInt(Scanner console, int min, int max) {
        int input = 0;
        boolean isInt = false;
        do {
            try {
                input = Integer.parseInt(console.nextLine());
                isInt = input >= min && input <= max;
            } catch (NumberFormatException e) {
                System.out.println("That's not an Integer");
                isInt = false;
            } catch (NoSuchElementException e) {
                System.out.printf("Scanner error: no element found%nNoSuchElementException%n");
                isInt = false;
            } catch (IllegalStateException e) {
                System.out.printf("Scanner error: scanner closed%IllegalStateException%n");
                isInt = false;
            }
            if (!isInt) {System.out.printf("Enter an Integer between %s and %s%n", min, max);};
        } while (!isInt);
        return input;
    }

    public static int getInt(Scanner console) {
        int input = 0;
        boolean isInt = false;
        do {
            try {
                System.out.println("Enter an Integer:");
                input = Integer.parseInt(console.nextLine());
                isInt = true;
            } catch (NumberFormatException e) {
                System.out.println("That's not an Integer");
                isInt = false;
            } catch (NoSuchElementException e) {
                System.out.printf("Scanner error: no element found%nNoSuchElementException%n");
                isInt = false;
            } catch (IllegalStateException e) {
                System.out.printf("Scanner error: scanner closed%IllegalStateException%n");
                isInt = false;
            }
        } while (!isInt);
        return input;
    }

    public static String getWord(Scanner console) {
        String input = "";
        boolean isWord = false;
        do {
            try {
                input = console.nextLine();
                isWord = input.matches("[a-zA-Z]+");
                if (!isWord) {
                    System.out.println("That's not a word");
                };
            } catch (NoSuchElementException e) {
                System.out.printf("Scanner error: probably no line element found%nNoSuchElementException%n");
                isWord = false;
            } catch (IllegalStateException e) {
                System.out.printf("Scanner error: scanner probably closed%IllegalStateException%n");
                isWord = false;
            }
            if (!isWord) {System.out.printf("Non-letter characters not allowed. Try again:");};
        } while (!isWord);
        return input;
    }
}
