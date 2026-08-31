package s6_matrix;

public class Hot48_rotate {
    /**
     * 48. 旋转图像
     * 给定一个 n x n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            // 注意 j 的起始位置，应该是 i + 1
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        Hot48_rotate hot48 = new Hot48_rotate();
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        hot48.rotate(matrix);
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
