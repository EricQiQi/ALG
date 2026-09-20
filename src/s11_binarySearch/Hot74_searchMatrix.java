package s11_binarySearch;

/**
 * 74. 搜索二维矩阵
 *
 * 给你一个满足下述两条属性的 m x n 整数矩阵：
 *
 * 每行中的整数从左到右按非严格递增顺序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 * 给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false 。
 *
 * 你必须编写一个时间复杂度为 O(log(m * n)) 的解决方案。
 *
 */
public class Hot74_searchMatrix {

    /**
     * 方法1：二分查找，先对第一列进行二分查找，找到目标行，然后对目标行进行二分查找，时间复杂度O(logm + logn) = O(logmn)
     * <p>
     * 对每一行元素的个数没有限制，比方法2的适用性更广
     */
    public boolean searchMatrix_1(int[][] matrix, int target) {
        if (matrix == null || matrix[0].length == 0) return false;
        int rows = matrix.length, cols = matrix[0].length;

        int rowIndex = firstColSearch(matrix, target, 0, rows - 1);
        if (rowIndex < 0) return false;
        if (matrix[rowIndex][0] == target) return true;

        return binarySearch(matrix[rowIndex], target, 0, cols - 1);
    }

    public int firstColSearch(int[][] matrix, int target, int up, int down) {
        while (up <= down) {
            int mid = up + (down - up) / 2;
            if (matrix[mid][0] > target) {
                down = mid - 1;
            } else if (matrix[mid][0] < target) {
                up = mid + 1;
            } else {
                return mid;
            }
        }

        return up - 1;
    }

    public boolean binarySearch(int[] nums, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 方法2：将二维矩阵看成一维矩阵进行二分
     * <p>
     * 限制：矩阵必须是规则的， 每一行元素的个数相同
     */
    public boolean searchMatrix_2(int[][] matrix, int target) {
        if (matrix == null || matrix[0].length == 0) return false;

        int rows = matrix.length, cols = matrix[0].length;
        int left = 0, right = rows * cols - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (matrix[mid / cols][mid % cols] > target) {
                right = mid - 1;
            } else if (matrix[mid / cols][mid % cols] < target) {
                left = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Hot74_searchMatrix hot74 = new Hot74_searchMatrix();
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        int target = 3;
        System.out.println(hot74.searchMatrix_1(matrix, target));
        System.out.println(hot74.searchMatrix_2(matrix, target));
    }
}
