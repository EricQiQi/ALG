package s10_backTracking;

/**
 * 79. 单词搜索
 *
 * 核心思路：以网格中每一个格子为起点做一次 DFS + 回溯，
 * 沿着 word 的字符逐个匹配上下左右四个方向，走过一格标记一格，
 * 四个方向都走不通就撤销标记（回溯）退回上一格换方向。
 * 用 visited 数组保证同一条路径里不重复经过同一格。
 */
public class Hot79_exist {

    public boolean exist(char[][] board, String word) {
        // 先判空再解引用，避免 board 为 null 时抛 NPE
        if (board == null || board.length == 0) return false;

        int rows = board.length;
        int cols = board[0].length;
        // 剪枝：路径不能重复走格子，单词最长只能有 rows*cols 个字符
        if (word.length() > rows * cols) return false;

        // 记录某格是否在当前路径上被访问过，回溯时会被撤销
        boolean[][] visited = new boolean[rows][cols];
        // 枚举每个格子作为单词起点，任一起点搜到就成功
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (dfs(board, word, visited, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 从 (row, col) 出发匹配 word 中下标为 start 的字符。
     * @return 能否从该格起匹配完 word 剩余的所有字符
     */
    public boolean dfs(char[][] board, String word, boolean[][] visited, int row, int col, int start) {
        // 所有字符都匹配完了，成功
        if (start == word.length()) return true;
        // 越界 / 已在当前路径上 / 当前格字符对不上，任一成立即失败
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || visited[row][col] || board[row][col] != word.charAt(start)) {
            return false;
        }

        visited[row][col] = true;  // 做选择：标记当前格已走

        // 向四个方向递归匹配下一个字符，|| 短路：任一方向成功即整体成功
        boolean found = dfs(board, word, visited, row-1, col, start + 1) ||
                dfs(board, word, visited, row+1, col, start + 1) ||
                dfs(board, word, visited, row, col-1, start + 1) ||
                dfs(board, word, visited, row, col+1, start + 1);

        visited[row][col] = false;  // 撤销选择：回溯，让其它路径能重新使用该格
        return found;
    }

    public static void main(String[] args) {
        Hot79_exist solution = new Hot79_exist();
        char[][] board = {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };
        String word = "ABCCED";
        boolean result = solution.exist(board, word);
        System.out.println(result);
    }
}
