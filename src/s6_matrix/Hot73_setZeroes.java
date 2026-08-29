package s6_matrix;

/**
 * 73. 矩阵置零
 * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的元素都设为 0 。请使用 原地 算法。
 * <p>
 * 进阶：
 * 一个直观的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
 * 一个简单的改进方法，使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
 * 你能想出一个仅使用常数空间的解决方案吗？
 */
public class Hot73_setZeroes {
    /**
     * 原地算法，使用两个标记数组，一个标记行中出现了0，一个标记列中出现了0
     * 时间复杂度：O(mn)
     * 空间复杂度：O(m + n)
     * @param matrix
     */
    public void setZeroes_1(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(matrix[i][j] == 0){
                    row[i] = true; col[j] = true;
                }
            }
        }

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(row[i] || col[j]){
                    matrix[i][j] = 0;
                }
            }
        }
    }

    /**
     * 原地算法，使用两个标记变量
     * 时间复杂度：O(mn)
     * 空间复杂度：O(1)
     * @param matrix
     */
    public void setZeroes_2(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        // 1. 用两个变量记录第一行和第一列原本有没有 0
        // 注意：第一行、第一列，发现0，不能直接把一行或一列覆盖，还要用作记账呢，所以不能直接覆盖
        boolean rowZero = false;
        boolean colZero = false;

        // 检查第一列
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                colZero = true;
                break;
            }
        }

        // 检查第一行
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                rowZero = true;
                break;
            }
        }

        // 2. 遍历内部所有格子，用第一行和第一列当“记事本”
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0; // 在当前行的开头做标记
                    matrix[0][j] = 0; // 在当前列的开头做标记
                }
            }
        }

        // 3. 根据“记事本”的标记，把内部的格子置零
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 4. 最后，根据第 1 步的记录，处理第一行和第一列自己
        if (colZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
        if (rowZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
    }

    /**
     * 原地算法，使用一个标记变量
     * 时间复杂度：O(mn)
     * 空间复杂度：O(1)
     * @param matrix
     */
    public void setZeroes_3(int[][] matrix) {
        int m = matrix.length;
        int n= matrix[0].length;

        // 表明第一列是否有0的存在
        boolean flagCol0 = false;
        for(int i=0; i<m; i++){
            if(matrix[i][0] == 0){
                flagCol0 = true;
            }

            for(int j=1; j<n; j++){
                if(matrix[i][j] == 0){
                    matrix[i][0] = matrix[0][j] = 0;
                }
            }
        }

        for(int i=m-1; i>=0; i--){
            for(int j=1; j<n; j++){
                if(matrix[i][0] == 0 || matrix[0][j] == 0){
                    matrix[i][j] = 0;
                }
            }
            if(flagCol0){
                matrix[i][0] = 0;
            }
        }
    }

    public static void main(String[] args) {
        Hot73_setZeroes hot73 = new Hot73_setZeroes();
//        int[][] matrix = {{1,2,3},{0,4,5},{6,7,8}};
//        int[][] matrix = {{1,1,1},{1,0,1},{1,1,1}};
        int[][] matrix = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        hot73.setZeroes_3(matrix);
        for(int[] row : matrix){
            for(int num : row){
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
