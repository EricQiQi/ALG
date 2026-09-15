package s9_graph;

/**
 * 200. 岛屿数量
 */
public class Hot200_numIslands_3 {

    /**
     * 并查集内部类
     * <p>
     * 核心计数思路：初始化时每块陆地自成一个集合（count = 陆地总数）；
     * 每次成功合并两个不同的集合，就少一座岛屿，count--。
     * 所以 count 实时就是当前剩余的连通分量（岛屿）数。
     */
    class UnionFind {
        int[] parent; // 父节点数组，parent[id] 指向 id 的父节点，根节点的父节点是自己
        int[] rank;   // 秩数组，记录根节点所在树的高度，用于按秩合并（矮树挂高树，防退化成链表）
        int count;    // 当前岛屿数量（连通分量个数）

        /**
         * 初始化：每个陆地格子的父节点指向自己（自成一座岛），水格子不初始化（不会参与合并）
         */
        public UnionFind(char[][] grid) {
            int rows = grid.length;
            int cols = grid[0].length;
            parent = new int[rows * cols];
            rank = new int[rows * cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == '1') {
                        int id = i * cols + j;
                        parent[id] = id;  // 初始化，每个节点的父节点是自己
                        count++; // 岛屿数量加一
                    }
                }
            }
        }

        /**
         * 查找 x 的根节点（所在集合的代表），顺便路径压缩：
         * 递归查找的路上把沿途节点的父节点直接改成根，下次再查就一步到位
         */
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);   // 路径压缩
            }
            return parent[x];
        }

        /**
         * 合并 x 和 y 所在的两个集合：
         * 已同根则不动；否则按秩合并（矮树挂到高树下），并且每合并一次 count--
         */
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) return;

            // 按秩合并
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;   // 矮树挂高树，高度不变
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;   // 等高时任挂一边，挂完后树长高一层
                rank[rootX]++;
            }

            count--;   // 两座岛合并成一座，岛屿数减一
        }

        public int getCount() {
            return count;
        }

    }

    /**
     * 方法3：并查集
     * <p>
     * 思路反过来：不是"见一座淹一座"，而是先假设每块陆地都是独立的岛，
     * 再扫描相邻关系把同属一座岛的陆地两两合并，每合并一次岛屿数减一。
     * <p>
     * 注意只需向右、向下看：从左上角扫描，(i,j) 与上方、左方的合并已在之前的迭代里做过，
     * 只处理右、下邻居就能覆盖所有相邻对，不会重复合并（union 同根时直接返回）。
     * <p>
     * 时间复杂度：O(m×n×α) ≈ O(m×n)，α 是反阿克曼函数，几乎等于常数
     * 空间复杂度：O(m×n)（parent 数组）
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int rows = grid.length;
        int cols = grid[0].length;
        UnionFind uf = new UnionFind(grid);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    grid[i][j] = '0';   // 合并过右、下邻居后淹没自己，防止后续重复处理
                    int id = i * cols + j;

                    // 只需看右、下两个方向的邻居
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

    /**
     * 测试用例：LeetCode 官方示例（与前两种解法相同，验证结果一致）
     */
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
