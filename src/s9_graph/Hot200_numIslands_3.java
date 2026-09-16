package s9_graph;

/**
 * 200. 岛屿数量
 */
public class Hot200_numIslands_3 {

    class UnionFind {
        int[] parent;
        int count;

        UnionFind(char[][] grid) {
            int rows = grid.length;
            int cols = grid[0].length;
            parent = new int[rows * cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == '1') {
                        int id = i * cols + j;
                        parent[id] = id;
                        count++;
                    }
                }
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) return;
            parent[rootX] = rootY;
            count--;
        }

        int getCount() {
            return count;
        }

    }


    /**
     * 方法3：并查集
     * 没有秩的版本
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int rows = grid.length;
        int cols = grid[0].length;

        UnionFind uf = new UnionFind(grid);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    // 每个格子本来只会被外层循环访问一次，置 '0' 既不影响右/下邻居的判断（判断的是别的格子），也不能防止重复处理。
                    // 它是从 DFS 解法里搬过来的习惯写法。副作用是修改了输入 grid，如果调用方后续还要用原矩阵，会有问题（不过 LeetCode 场景无所谓）。
//                     grid[i][j] = '0';

                    int id = i * cols + j;
                    if (i + 1 < rows && grid[i + 1][j] == '1') {
                        uf.union(id, (i + 1) * cols + j);
                    }
                    if (j + 1 < cols && grid[i][j + 1] == '1') {
                        uf.union(id, i * cols + j + 1);
                    }
                }
            }
        }
        return uf.getCount();
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        int result_1 = new Hot200_numIslands_3().numIslands(grid);
        System.out.println("岛屿数量: " + result_1); // 期望输出：3
    }
}
