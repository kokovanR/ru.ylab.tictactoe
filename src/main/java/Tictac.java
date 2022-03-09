import java.io.IOException;
import java.nio.file.*;
import java.util.*;


public class Tictac {

    final char _x = 'x', o = 'o', _dot = '.';
    char[][] table;
    String playerName;
    Random random;
    Scanner scanner;

    public static void main(String[] args) {
        try {
            new Tictac().game();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Tictac() {
        random = new Random();
        scanner = new Scanner(System.in);
        table = new char[3][3];
    }

    // logic

    void game() throws IOException { //throws FileNotFoundException {
        //File file = new File("Scores");
        //PrintWriter write = new PrintWriter(file);
        System.out.println("Ваше имя :");
        this.playerName = scanner.nextLine();
        field(); // инициализация таблицы

        do {
            while(true) {
                move();
                if (win(_x)) {
                    System.out.println(this.playerName + " выиграл");
                    String text = "\n" + this.playerName + "+";
                    Files.write(Paths.get("Scores.txt"), text.getBytes(), StandardOpenOption.APPEND);
                    //write.
                    //write.close();
                    break;
                }
                if (fieldFull()) {
                    System.out.println("Ничья");
                    break;
                }
                bot();
                printField();
                if (win(o)) {
                    System.out.println("Бот выиграл");
                    String text = "\nБот+";
                    Files.write(Paths.get("Scores.txt"), text.getBytes(), StandardOpenOption.APPEND);
                    //write.append("Бот+");
                    //write.close();
                    break;
                }
                if (fieldFull()) {
                    System.out.println("Ничья");
                    break;
                    }
                }


        System.out.println("Всё");
        printField();
        field();
        System.out.println("Ещё раз ? Да - 0, Нет - 1");
    } while (scanner.nextInt() == 0);
    }


    // feat.

    void field() {
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                table[row][col] = _dot;
    }

    void printField() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++)
                System.out.print(table[row][col] + " ");
            System.out.println();
        }
    }

    void move() {
        int x, y;
        do {
            System.out.println("Enter X and Y (1..3):");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!empty(x, y));
        table[y][x] = _x;
    }

    boolean empty(int x, int y) {
        if (x < 0 || y < 0 || x >= 3|| y >= 3)
            return false;
        return table[y][x] == _dot;
    }

    void bot() {
        int x, y;
        do {
            x = random.nextInt(3);
            y = random.nextInt(3);
        } while (!empty(x, y));
        table[y][x] = o;
    }

    boolean win(char dot) {
        for (int i = 0; i < 3; i++)
            if ((table[i][0] == dot && table[i][1] == dot &&
                    table[i][2] == dot) ||
                    (table[0][i] == dot && table[1][i] == dot &&
                            table[2][i] == dot))
                return true;
        if ((table[0][0] == dot && table[1][1] == dot &&
                table[2][2] == dot) ||
                (table[2][0] == dot && table[1][1] == dot &&
                        table[0][2] == dot))
            return true;
        return false;
    }

    boolean fieldFull() {
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (table[row][col] == _dot)
                    return false;
        return true;
    }
}