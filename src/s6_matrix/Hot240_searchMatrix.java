package s6_matrix;

/**
 * 240. 搜索二维矩阵 II
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 */
public class Hot240_searchMatrix {

    /**
     * 方法一：暴力法
     * 时间复杂度：O(mn)
     * 空间复杂度：O(1)
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix_1(int[][] matrix, int target) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 方法二：二分法
     * 时间复杂度：O(mlogn)
     * 空间复杂度：O(1)
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix_2(int[][] matrix, int target) {
        for (int[] row : matrix) {
            if (binarySearch(row, target)) {
                return true;
            }
        }
        return false;
    }
    private boolean binarySearch(int[] row, int target) {
        int left = 0, right = row.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (row[mid] == target) {
                return true;
            } else if (row[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    /**
     * 方法三：z字形查找
     * 时间复杂度：O(m + n)
     * 空间复杂度：O(1)
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix_3(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Hot240_searchMatrix hot240 = new Hot240_searchMatrix();
        int[][] matrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
        boolean res1 = hot240.searchMatrix_1(matrix, 5);
        boolean res2 = hot240.searchMatrix_2(matrix, 5);
        boolean res3 = hot240.searchMatrix_1(matrix, 5);

    }
}
