public class Main {

    public static void main (String[] args) {
        System.out.printf("m(20,10)=%d%n",m(20,10));
    }

    static int m (int x, int y) {
        while (x > 10) {
            x = x - 10;
            if (x == 10) break;
        }
        if (y < 20 && x % 2 == 0) {
            y = y + 20;
        }
        else {
            y = y - 20;
        }
        return 2 * x + y;
    }

}
