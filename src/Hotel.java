import java.util.Arrays;
import java.util.Scanner;

public class Hotel {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        String[] capsules = new String[roomInit(console)];
        boolean exit = false;

        do {

            printMenu();
            int input = Input.getInt(console, 1, 4);

            switch (input) {
                case 1:
                    if (getHotelStatus(capsules) == "full") {
                        System.out.printf(Const.F.ERROR, Const.S.FULL);
                        break;
                    }
                    checkIn(console, capsules);
                    break;
                case 2:
                    if (getHotelStatus(capsules) == "empty") {
                        System.out.printf(Const.F.ERROR, Const.S.EMPTY);
                        break;
                    }
                    checkOut(console, capsules);
                    break;
                case 3:
                    System.out.printf(Const.F.CAPSULE_INPUT, capsules.length);
                    int capsule = Input.getInt(console,1,capsules.length);
                    view(console, capsules, capsule-1);
                    break;
                case 4:
                    exit = exitMenu(console);
                    break;
                default:
                    System.out.printf(Const.F.ERROR);
            }

        } while (!exit);
    }

    /**
     * printMenu contains a series of System.out.printf and println using the Consts class to populate.
     */
    public static void printMenu() {
        System.out.printf( "%n%s%n%s%n%s%n", "=".repeat(Const.S.MENU.length()), Const.S.MENU, "=".repeat(Const.S.MENU.length()));
        System.out.println("1. "+Const.S.CHECK_IN);
        System.out.println("2. "+Const.S.CHECK_OUT);
        System.out.println("3. "+Const.S.VIEW);
        System.out.println("4. "+Const.S.EXIT);
        System.out.printf(Const.F.CHOOSE_OPTION, 1, 4);
    }

    /**
     * roomInit contains array building prompts and returns a length after input validation.
     * Does not build the array.
     * @param console
     * @return integer to be used as array length
     */
    public static int roomInit(Scanner console) {
        System.out.printf("%n%n%s%n", String.format(Const.F.WELCOME,"=".repeat(Const.F.WELCOME.length()), "=".repeat(Const.F.WELCOME.length())));
        System.out.print(Const.S.CAPSULE_REQUEST);
        int input = Input.getInt(console, 1);
        System.out.printf(Const.F.READY_CAPSULES, input);
        return input;
    }

    /**
     * getHotelStatus counts an array length. Always outputs a message
     * from Consts class saying how full.
     * @param capsules array of names
     * @return string "good", "full", or "empty"
     */
    public static String getHotelStatus(String[] capsules) {
        int count = 0;
        String status = "";
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

    /**
     * checkIn validates console inputs to add a name to capsules array. Uses Consts class messages. Uses view method
     * after user inputs a room number to show them who is in it and neighboring rooms. Re-prompts if room value is not
     * null.
     * @param console
     * @param capsules array of names
     */
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

    /**
     * checkOut validates console inputs to replace a name in capsules array with null. Uses Consts class messages.
     * Uses view method after user inputs a room number to show them who is in it and neighboring rooms. Re-prompts if
     * room value is already null.
     * @param console
     * @param capsules array of names
     */
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

    /**
     * Zero-indexing. view picks a range of index-5 -> index+5, or the bottom 11 or the top 11 indexes of the capsules
     * array. It calls printHorizontal using the start and end indexes it picked. If capsules.length < 11 then .length-1
     * is the end index.
     * @param console
     * @param capsules array of names
     * @param index The view center. index-5 to index+5 will be printed.
     */
    public static void view(Scanner console, String[] capsules, int index) {
        int right = capsules.length - 1 -index;
        if (capsules.length < 11) {
            printVertical(capsules, 0, capsules.length-1);
            // printHorizontal(capsules, 0, capsules.length-1);
        } else if (index - 5 <= 0) {
            printVertical(capsules, 0, 10);
            // printHorizontal(capsules, 0, 10);
        } else if (right < 5) {
            printVertical(capsules, capsules.length-11, capsules.length-1);
            // printHorizontal(capsules, capsules.length-11, capsules.length-1);
        } else {
            printVertical(capsules, index - 5, index + 5);
            // printHorizontal(capsules, index - 5, index + 5);
        }
    }

    /**
     * Zero-indexing. Logs each index and name of the array on a new line.
     * @param capsules array of names
     * @param startIncluded first index to be printed to the console
     * @param endIncluded last index to be printed to the console
     */
    public static void printVertical(String[] capsules,  int startIncluded, int endIncluded) {
        System.out.println("Capsule | Guest Name");
        System.out.println("======= | =========");
        for (int i = startIncluded; i <= endIncluded; i++) {
            System.out.printf("%7s | %s %n", i+1, capsules[i] == null ? "[unoccupied]" : capsules[i]);
        }
    }

    // TODO: Talk to Irina about optimization of print out

    /**
     * Zero-indexing. Arranges array values and indexes into two horizontal format strings and logs them to the console
     * on two lines.
     * @param capsules array of names
     * @param startIncluded first index to be printed to the console
     * @param endIncluded last index to be printed to the console
     */
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

    /**
     * exitMenu verifies via console that the user would indeed like to exit the application, and returns true if so.
     * Uses Consts class to populate menu.
     * @param console
     * @return boolean true if the user wants to exit. False otherwise.
     */
    public static boolean exitMenu(Scanner console) {
        System.out.printf(Const.F.EXIT_MESSAGE);
        int input = Input.getInt(console, 1,2);
        boolean exit = input == 1;
        if (exit) {System.out.println(Const.S.BYE);}
        return exit;
    }






























}
