package s9_graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 994. 腐烂的橘子
 * <p>
 * 多源 BFS：所有初始腐烂橘子同时入队作为第 0 层，每分钟向外扩散一层，
 * 答案 = 传染完所有新鲜橘子所用的层数（分钟数）。
 * 相当于建一个虚拟超级源点连着所有腐烂橘子，再做单源 BFS。
 */
public class Hot994_orangesRotting {

    /**
     * 广度优先搜索（BFS）
     * 返回直到没有新鲜橘子为止必须经过的最小分钟数；若永远传染不完返回 -1
     * 时间复杂度：O(m * n)，每个格子最多入队一次
     * 空间复杂度：O(m * n)，队列最坏存下全部格子
     */
    public int orangesRotting(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int freshCount = 0;                          // 新鲜橘子计数，传染时同步扣减
        Queue<int[]> queue = new LinkedList<>();

        // 第一遍扫描：腐烂橘子全部入队（多源 BFS 的“多源”），顺便数新鲜橘子
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});     // 腐烂橘子：第 0 层起点
                } else if (grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }

        // 没有新鲜橘子：0 分钟（含全空网格的情况）
        if (freshCount == 0) return 0;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int minutes = 0;

        // freshCount > 0 是关键：最后一层染完就退出，minutes 不会多算一轮
        while (!queue.isEmpty() && freshCount > 0) {
            minutes++;                               // 每处理一层 = 过去一分钟
            int size = queue.size();                 // 锁住当前层，本层新入队的属于下一层
            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int row = current[0];
                int col = current[1];

                for (int[] direction : directions) {
                    int newRow = row + direction[0];
                    int newCol = col + direction[1];
                    // 邻居是新鲜橘子才传染；入队的同时立刻标 2，防止被多个腐烂邻居重复入队
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == 1) {
                        queue.offer(new int[]{newRow, newCol});
                        grid[newRow][newCol] = 2;
                        freshCount--;                 // 染一个记一个
                    }
                }
            }
        }

        // 队列空了还有新鲜橘子 = 永远传染不到
        return freshCount == 0 ? minutes : -1;
    }

    /**
     * 测试用例：LeetCode 官方示例，腐烂橘子逐分钟扩散（2→3 层→4 层），共 4 分钟
     */
    public static void main(String[] args) {
        Hot994_orangesRotting hot994 = new Hot994_orangesRotting();
        int[][] grid = {{2, 1, 1}, {1, 1, 0}, {0, 1, 1}};
        System.out.println(hot994.orangesRotting(grid));   // 期望输出：4
    }
}
