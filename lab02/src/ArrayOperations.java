/** Array Operations Class. Optional Exercise **/
public class ArrayOperations {
    /**
     * Delete the value at the given position in the argument array, shifting
     * all the subsequent elements down, and storing a 0 as the last element of
     * the array.
     */
    public static void delete(int[] values, int pos) {
        if (pos < 0 || pos >= values.length) {
            return;
        }else {
            int index = pos;
            while (index < values.length - 1) {
                values[index] = values[index + 1];
                index += 1;
            }
            values[values.length - 1] = 0;
            return;
        }
    }

    /**
     * Insert newInt at the given position in the argument array, shifting all
     * the subsequent elements up to make room for it. The last element in the
     * argument array is lost.
     */
    public static void insert(int[] values, int pos, int newInt) {
        if (pos < 0 || pos >= values.length) {
            return;
        }
        for ( int index = values.length - 1; index >= pos; index -= 1){
            if (index == pos) {
                values[index] = newInt;
            }else {
                values[index] = values[index - 1];
            }
        }
        return;
    }

    /** 
     * Returns a new array consisting of the elements of A followed by the
     *  the elements of B. 
     */
    public static int[] catenate(int[] A, int[] B) {
        int[] conbinationarr = new int[A.length + B.length];
        for (int index = 0; index < A.length + B.length; index += 1){
            if (index < A.length) {
                conbinationarr[index] = A[index];
            }else {
                conbinationarr[index] = B[index - A.length];
            }
        }
        return conbinationarr;
    }
}
