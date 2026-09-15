package s9_graph;

/**
 * 200. 岛屿数量
 */
public class Hot200_numIslands_1_DFS_tempArr {

    /**
     * 方法1：深度优先搜索 dfs
     * 使用临时数组记录访问过的陆地，不直接改原数组
     *
     * 时间复杂度：O(m * n)
     * 空间复杂度：O(m * n)
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int rows = grid.length;
        int cols = grid[0].length;
        int islands_num = 0;

        // 记录访问过的陆地，默认为 false，表示未访问
        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 是陆地且未访问过
                if (grid[i][j] == '1' && !visited[i][j]) {
                    islands_num++;
                    dfs(grid, visited, i, j);
                }
            }
        }
        return islands_num;
    }

    public void dfs(char[][] grid, boolean[][] visited, int row, int col) {
        int rows = grid.length;
        int cols = grid[0].length;

        // 越界或遇到水或已访问过，直接返回
        if (row < 0 || col < 0 || row >= rows || col >= cols
                || grid[row][col] != '1' || visited[row][col]) return;

        // 标记访问，代替 grid[row][col] = '0'
        visited[row][col] = true;

        dfs(grid, visited, row - 1, col);
        dfs(grid, visited, row + 1, col);
        dfs(grid, visited, row, col - 1);
        dfs(grid, visited, row, col + 1);
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

        int result_1 = new Hot200_numIslands_1_DFS_tempArr().numIslands(grid);
        System.out.println("岛屿数量: " + result_1); // 期望输出：3
    }
}
