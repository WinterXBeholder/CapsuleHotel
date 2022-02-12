import java.util.Arrays;
import java.util.Scanner;

public class Hotel {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        String[] capsules = new String[roomInit(console)];
        boolean exit = false;

        do {
            exit = false;
            String input;
            printMenu();
            input = Integer.toString(Input.getInt(console, 1, 4));

            switch (input) {
                case "1":
                    if (getHotelStatus(capsules) == "full") {
                        System.out.printf(Const.F.ERROR, Const.S.FULL);
                        break;
                    }
                    checkIn(console, capsules);
                    break;
                case "2":
                    if (getHotelStatus(capsules) == "empty") {
                        System.out.printf(Const.F.ERROR, Const.S.EMPTY);
                        break;
                    }
                    checkOut(console, capsules);
                    break;
                case "3":
                    System.out.printf(Const.F.CAPSULE_INPUT, capsules.length);
                    int capsule = Input.getInt(console,1,capsules.length);
                    view(console, capsules, capsule-1);
                    break;
                case "4":
                    exit = exitMenu(console);
                    break;
                default:
                    System.out.printf(Const.F.ERROR);
            }

        } while (!exit);
    }

    public static void printMenu() {
        System.out.printf( "%n%s%n%s%n%s%n", "=".repeat(Const.S.MENU.length()), Const.S.MENU, "=".repeat(Const.S.MENU.length()));
        System.out.println("1. "+Const.S.CHECK_IN);
        System.out.println("2. "+Const.S.CHECK_OUT);
        System.out.println("3. "+Const.S.VIEW);
        System.out.println("4. "+Const.S.EXIT);
        System.out.printf(Const.F.CHOOSE_OPTION, 1, 4);
    }

    public static int roomInit(Scanner console) {
        System.out.printf("%n%n%s%n", String.format(Const.F.WELCOME,"=".repeat(Const.F.WELCOME.length()), "=".repeat(Const.F.WELCOME.length())));
        System.out.print(Const.S.CAPSULE_REQUEST);
        int input = Input.getInt(console, 1);
        System.out.printf(Const.F.READY_CAPSULES, input);
        return input;
    }

    public static String getHotelStatus(String[] capsules) {
        int count = 0;
        String status = "good";
        for (int i = 0; i < capsules.length; i++) {
            if (capsules[i] != null) { count += 1;}
        }
        if (count == capsules.length) {
            status = "full";
        } else if (count == 0) {
            status = "empty";
        }
        System.out.printf(Const.F.STATUS, count, capsules.length);
        return status;
    }

    public static void checkIn(Scanner console, String[] capsules) {
        System.out.printf("%n%s%n%s%n%s%n", "=".repeat(Const.S.CHECK_IN.length()), Const.S.CHECK_IN, "=".repeat(Const.S.CHECK_IN.length()));
        System.out.printf("%s: ", Const.S.GUEST_NAME);
        String name = Input.getWord(console);
        int capsule;
        boolean occupied = true;
        do {
            System.out.printf(Const.F.CAPSULE_INPUT, capsules.length);
            capsule = Input.getInt(console, 1, capsules.length);
            view(console, capsules, capsule-1);
            if (capsules[capsule-1] == null) {
                occupied = false;
                capsules[capsule-1] = name;
                System.out.printf(Const.F.SUCCESS,
                        String.format(Const.F.CHECKED_IN, capsules[capsule-1], capsule));
            } else {
                System.out.printf(Const.F.ERROR, String.format(Const.F.OCCUPIED, capsule));
            }
        } while (occupied);
    }

    public static void checkOut(Scanner console, String[] capsules) {
        System.out.printf("%n%s%n%s%n%s%n", "=".repeat(Const.S.CHECK_OUT.length()), Const.S.CHECK_OUT, "=".repeat(Const.S.CHECK_OUT.length()));
        int capsule;
        boolean occupied = false;
        do {
            System.out.printf(Const.F.CAPSULE_INPUT, capsules.length);
            capsule = Input.getInt(console, 1, capsules.length);
            view(console, capsules, capsule-1);
            if (capsules[capsule-1] != null) {
                occupied = true;
                System.out.printf(Const.F.SUCCESS,
                        String.format(Const.F.CHECKED_OUT, capsules[capsule-1], capsule));
                capsules[capsule-1] = null;
            } else {
                System.out.printf(Const.F.ERROR, String.format(Const.F.UNOCCUPIED, capsule));
            }
        } while (!occupied);
    }

    public static void view(Scanner console, String[] capsules, int index) {
        int right = capsules.length - 1 -index;
        if (capsules.length < 11) {
            printHorizontal(capsules, 0, capsules.length-1);
        } else if (index - 5 <= 0) {
            printHorizontal(capsules, 0, 10);
        } else if (right < 5) {
            printHorizontal(capsules, capsules.length-11, capsules.length-1);
        } else {
            printHorizontal(capsules, index - 5, index + 5);
        }
    }

    public static void printHorizontal(String[] capsules, int startIncluded, int endIncluded) {
        String rooms = Const.S.CAPSULE+"->| %-20s | %-20s | %-20s | %-20s |  %-20s |  %-20s |  %-20s |  %-20s |  %-20s |  %-20s |  %-20s |%n";
        String names = Const.S.GUEST+"  ->| %-20s | %-20s | %-20s | %-20s |  %-20s |  %-20s |  %-20s |  %-20s |  %-20s |  %-20s |  %-20s |%n";
        String[][] numberName = new String[2][11];
        Arrays.fill(numberName[0], "");
        Arrays.fill(numberName[1], "");
        for (int i = startIncluded, j = 0; i <= endIncluded; i++, j++) {
            numberName[0][j] = Integer.toString(i+1); // add one to translate from index to room number
            numberName[1][j] = capsules[i] == null ? "[unoccupied]" : capsules[i];
        }
        rooms = String.format(rooms,
                numberName[0][0],
                numberName[0][1],
                numberName[0][2],
                numberName[0][3],
                numberName[0][4],
                numberName[0][5],
                numberName[0][6],
                numberName[0][7],
                numberName[0][8],
                numberName[0][9],
                numberName[0][10]
                );
        names = String.format(names,
                numberName[1][0],
                numberName[1][1],
                numberName[1][2],
                numberName[1][3],
                numberName[1][4],
                numberName[1][5],
                numberName[1][6],
                numberName[1][7],
                numberName[1][8],
                numberName[1][9],
                numberName[1][10]
        );
        System.out.printf(rooms + names);
    }

    public static boolean exitMenu(Scanner console) {
        System.out.printf(Const.F.EXIT_MESSAGE);
        int input = Input.getInt(console, 1,2);
        boolean exit = input == 1;
        if (exit) { System.out.println(Const.S.BYE);}
        return exit;
    }






























}
