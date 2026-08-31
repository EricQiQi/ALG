package s6_matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * 54. 螺旋矩阵
 *
 */
public class Hot54_spiralOrder {

    /**
     * 螺旋矩阵
     * 给定一个 m x n 的矩阵 matrix ，按螺旋的顺序返回矩阵中的所有元素。
     * 时间复杂度：O(m * n)
     * 空间复杂度：O(1)
     * @param matrix
     * @return
     */
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();

        // 边界情况
        if (matrix == null || matrix.length == 0) {
            return result;
        }

        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            // 1. 从左到右遍历 top 行
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            top++; // 上边界下移

            // 2. 从上到下遍历 right 列
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--; // 右边界左移

            // 3. 从右到左遍历 bottom 行（注意判断是否重复）
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    result.add(matrix[bottom][i]);
                }
                bottom--; // 下边界上移
            }

            // 4. 从下到上遍历 left 列（注意判断是否重复）
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++; // 左边界右移
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Hot54_spiralOrder hot54 = new Hot54_spiralOrder();
//        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrix = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}};
        List<Integer> result = hot54.spiralOrder(matrix);
        for (int num : result) {
            System.out.print(num + " ");
        }
    }
}
