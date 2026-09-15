package s9_graph;

/**
 * 200. 岛屿数量
 * <p>
 * 给你一个由 '1'（陆地）和 '0'（水）组成的二维网格，请你计算网格中岛屿的数量。
 * <p>
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向或竖直方向上相邻的陆地连接形成。
 * <p>
 * 此外，你可以假设该网格的四条边均被水包围。
 */
public class Hot200_numIslands_1_DFS {

    /**
     * 方法1：深度优先搜索 dfs
     * 时间复杂度：O(m * n)
     * 空间复杂度：O(m * n)
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
                    dfs(grid, i, j);
                }
            }
        }
        return islands_num;
    }

    /**
     * 深度优先搜索：把当前格子及与之相连的所有陆地格子"淹没"为水（'1' → '0'）
     * <p>
     * 越界或遇到水（'0'）时直接返回，否则先淹没当前格子，再向上下左右四个方向扩展
     */
    public void dfs(char[][] grid, int row, int col) {
        int rows = grid.length;
        int cols = grid[0].length;

        // 越界或遇到水，直接返回
        if (row < 0 || col < 0 || row >= rows || col >= cols || grid[row][col] == '0') return;

        // 淹没当前陆地，避免重复访问
        grid[row][col] = '0';

        // 向上下左右四个方向扩展，淹没整块岛屿
        dfs(grid, row - 1, col);
        dfs(grid, row + 1, col);
        dfs(grid, row, col - 1);
        dfs(grid, row, col + 1);
    }




    /**
     * 测试用例：LeetCode 官方示例
     * <p>
     * 1 1 0 0 0
     * 1 1 0 0 0
     * 0 0 1 0 0
     * 0 0 0 1 1
     * 共 3 座岛屿：左上角的 2x2 陆地、中间的单独陆地、右下角的两个陆地
     */
    public static void main(String[] args) {
        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        int result_1 = new Hot200_numIslands_1_DFS().numIslands(grid);
        System.out.println("岛屿数量: " + result_1); // 期望输出：3
    }
}
