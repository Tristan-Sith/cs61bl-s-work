public class TriangleDrawer2 {

    public static void drawTriangle () {
        int size = 5;
        for (int col = 0; col < size; col += 1){
            for (int row = 0; row <= col; row += 1){
                System.out.print("*");
            }
            System.out.println();
        }
    }

    public static void main (String[] args) {
        drawTriangle();
    }
}
