package s9_graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 200. 岛屿数量（BFS）
 */
public class Hot200_numsIslangs_2_BFS {

    /**
     * 方法2：广度优先搜索 BFS
     * <p>
     * 和 DFS 同一套"见一数一，数完就淹"框架，区别只是淹没的方式：
     * DFS 靠递归栈一路沉到底，BFS 靠队列一层层向外扩散。
     * 队列里存的是格子的整数编码 id = row * cols + col，出队时再反解出行列。
     * <p>
     * 时间复杂度：O(m * n)
     * 空间复杂度：O(min(m, n))，队列中最坏情况存的是对角线斜条上的格子数
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int rows = grid.length;
        int cols = grid[0].length;
        int islands_num = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    islands_num++;
                    grid[i][j] = '0';   // 入队前就淹没，防止重复入队

                    Queue<Integer> queue = new LinkedList<>();
                    queue.offer(i * cols + j);   // 格子编码：id = row * cols + col

                    while (!queue.isEmpty()) {
                        int id = queue.poll();
                        int row = id / cols;   // 反解出行号
                        int col = id % cols;   // 反解出列号

                        // 四个方向的邻居，是陆地就淹没并入队
                        if (row - 1 >= 0 && grid[row - 1][col] == '1') {
                            queue.offer((row - 1) * cols + col);
                            grid[row - 1][col] = '0';
                        }
                        if (row + 1 < rows && grid[row + 1][col] == '1') {
                            queue.offer((row + 1) * cols + col);
                            grid[row + 1][col] = '0';
                        }
                        if (col - 1 >= 0 && grid[row][col - 1] == '1') {
                            queue.offer(row * cols + col - 1);
                            grid[row][col - 1] = '0';
                        }
                        if (col + 1 < cols && grid[row][col + 1] == '1') {
                            queue.offer(row * cols + col + 1);
                            grid[row][col + 1] = '0';
                        }
                    }

                }
            }
        }

        return islands_num;
    }

    /**
     * 测试用例：LeetCode 官方示例（与方法1 DFS 相同，验证两种解法结果一致）
     */
    public static void main(String[] args) {
        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        int result_1 = new Hot200_numsIslangs_2_BFS().numIslands(grid);
        System.out.println("岛屿数量: " + result_1); // 期望输出：3
    }
}
