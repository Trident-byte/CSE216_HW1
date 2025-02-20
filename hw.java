
class hw {

    public static void main(String[] args) {
        int[] test = {1, 2, 5};
        int[][] ans = suffices(test);
        System.out.print("[");
        for (int[] array : ans) {
            printArray(array);
            System.out.print(";");
        }
        System.out.print("]");
    }

    private static int[][] recSuffices(int[][] acc, int[] array, int initialLength) {
        if (array.length == 0) {
            acc[initialLength] = array;
            return acc;
        } else {
            int[] newArray = new int[array.length - 1];
            System.out.println(initialLength - array.length);
            acc[initialLength - array.length] = array;
            System.arraycopy(array, 0, newArray, 0, array.length - 1);
            return recSuffices(acc, newArray, initialLength);
        }
    }

    private static int[][] suffices(int[] array) {
        int[][] ans = new int[array.length + 1][];
        return recSuffices(ans, array, array.length);
    }

    private static void printArray(int[] array) {
        String result = "[";
        if (array != null) {

            for (int num : array) {
                result += num + ", ";
            }
            if (result.length() != 1) {
                result = result.substring(0, result.length() - 2);
            }
            System.out.print(result + "]");
        }
    }
}
