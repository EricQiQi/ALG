package s9_graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 207. 课程表
 * <p>
 * 拓扑排序 / 有向图判环：能否修完全部课 = 先修关系构成的有向图里有没有环。
 * 本文件用 DFS 三色标记法：visited 0=未访问、1=在当前 DFS 路径上、2=该点出发的所有路径已检查完；
 * 一旦在递归途中撞到状态 1 的点，说明绕回了当前路径 → 有环。
 */
public class Hot207_canFinish_DFS {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 1. 把枯燥的数组转换成“图”（邻接表）
        // 比如 graph.get(1) 里面装的都是学完 1 之后才能学的后续课程
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] pre : prerequisites) {
            // pre = [想要学的课, 必须先学的课] -> pre[0], pre[1]
            // 所以方向是：学完 pre[1] -> 解锁 pre[0]
            graph.get(pre[1]).add(pre[0]);
        }

        // 2. 状态数组（0: 没查过, 1: 正在查, 2: 查过且安全）
        int[] visited = new int[numCourses];

        // 3. 对每一门还没查过的课，进行顺藤摸瓜 (DFS)
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                // 如果在顺藤摸瓜的过程中发现了死循环（返回 false），那直接整体完蛋
                if (!dfs(graph, visited, i)) {
                    return false;
                }
            }
        }

        // 所有的课都查过了，都没发现死循环，恭喜，可以毕业！
        return true;
    }

    // DFS 顺藤摸瓜函数
    private boolean dfs(List<List<Integer>> graph, int[] visited, int course) {
        // 如果顺藤摸瓜碰到了一门“正在查”的课，说明绕成环了！
        if (visited[course] == 1) {
            return false;
        }
        // 如果碰到了已经确认“安全”的课，说明这条路没问题，直接放行
        if (visited[course] == 2) {
            return true;
        }

        // --- 走到这里说明是一门没查过的课 (visited[course] == 0) ---

        // 把当前这门课标记为“正在查”（打上黄色标签）
        visited[course] = 1;

        // 遍历所有学完这门课就能解锁的“后续课程”
        for (int nextCourse : graph.get(course)) {
            // 继续往下摸瓜，如果有任何一条路发现了死循环，直接把坏消息传回去
            if (!dfs(graph, visited, nextCourse)) {
                return false;
            }
        }

        // 这门课的所有后续之路都走通了，没有死循环。
        // 给它盖上“安全”的章（打上绿色标签）
        visited[course] = 2;

        return true;
    }

    public static void main(String[] args) {
        Hot207_canFinish_DFS solution = new Hot207_canFinish_DFS();
        int numCourses = 4;
        int[][] prerequisites = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        System.out.println(solution.canFinish(numCourses, prerequisites));
    }
}
