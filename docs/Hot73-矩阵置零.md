> **⚠️ 常见误区解答：** 为什么不能在第一步发现第一行有 `0` 时，直接顺手把第一行全变 `0`？
> **答案：** 如果提前变 `0`，第一行就全黑了。接下来内部格子去对账时，会误以为它们所在的整列都要清零，导致信息丢失，全盘翻车！

```java
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
```