package s10_backTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 51. N 皇后
 */
public class Hot51_solveNQueens {

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();

        // 棋盘：'.' 表示空格，'Q' 表示皇后
        char[][] grid = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = '.';
            }
        }

        // 从第 0 行开始逐行放置皇后
        backTrack(res, grid, 0, n);
        return res;
    }

    public void backTrack(List<List<String>> res, char[][] grid, int row, int n) {
        // 递归出口：n 行都成功放下皇后，收割一份完整棋盘
        if (row == n) {
            res.add(construct(grid));
            return;
        }

        // 在当前 row 行里，依次尝试把皇后放到每一列
        for (int col = 0; col < n; col++) {
            if (isValid(grid, row, col, n)) {
                grid[row][col] = 'Q';              // 做选择：放下皇后
                backTrack(res, grid, row + 1, n);  // 递归处理下一行
                grid[row][col] = '.';              // 撤销选择：回溯，换下一列再试
            }
        }
    }

    public boolean isValid(char[][] grid, int row, int col, int n){
        // 逐行放置，皇后只可能出现在当前行上方，故只需向上检查三个方向
        // 检查正上方同列
        for(int i=row-1; i>=0; i--){
            if(grid[i][col] == 'Q') return false;
        }

        // 检查左上方对角线（行、列同时递减）
        for(int i=row-1, j=col-1; i>=0 && j>=0; i--, j--){
            if(grid[i][j] == 'Q') return false;
        }

        // 检查右上方对角线（行递减、列递增）
        for(int i=row-1, j=col+1; i>=0 && j<n; i--, j++){
            if(grid[i][j] == 'Q') return false;
        }

        return true;
    }

    public List<String> construct(char[][] grid) {
        // 把二维棋盘按行拼成字符串列表；new String(grid[i]) 会拷贝，不受后续回溯影响
        List<String> output = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            output.add(new String(grid[i]));
        }
        return output;
    }

    public static void main(String[] args) {
        Hot51_solveNQueens hot51 = new Hot51_solveNQueens();
        List<List<String>> res = hot51.solveNQueens(4);
        System.out.println(res);
    }
}
