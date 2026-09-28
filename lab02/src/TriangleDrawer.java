public class TriangleDrawer {

    public static void drawTriangle() {
        int col = 0;
        int row = 0;
        int SIZE = 5;
        while (col < SIZE) {
            row = 0;
            col += 1;
            while (row < col) {
                System.out.print('*');
                row += 1;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        drawTriangle();
    }
}