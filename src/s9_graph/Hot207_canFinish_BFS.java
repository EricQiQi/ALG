package s9_graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 207. 课程表
 */
public class Hot207_canFinish_BFS {

    /**
     * 拓扑排序
     * 时间复杂度：O(V+E)
     * 空间复杂度：O(V+E)
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 建立图
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<numCourses; i++){
            graph.add(new ArrayList<>());
        }
        // 初始化图，初始化入度 = 指向该节点的路径数
        int[] inDegree = new int[numCourses];
        for(int[] pre : prerequisites){
            // 题目中prerequisites = [[1,0]]   ->   graph[0] = [1]  后面的是需要先修的课程，作为根节点
            graph.get(pre[1]).add(pre[0]);

            // 被指向节点的入度加一
            inDegree[pre[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i=0; i<numCourses; i++){
            // 度为0的节点入队
            if(inDegree[i] == 0){
                queue.offer(i);
            }
        }

        // 计算节点数
        int count = 0;

        while(!queue.isEmpty()){
            int course = queue.poll();
            count++;

            for(int nextCourse : graph.get(course)){
                inDegree[nextCourse]--;
                if(inDegree[nextCourse] == 0){
                    queue.offer(nextCourse);
                }
            }
        }

        return count == numCourses;

    }

    public static void main(String[] args) {
        Hot207_canFinish_BFS canFinish = new Hot207_canFinish_BFS();
        int numCourses = 4;
//        int[][] prerequisites = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        int[][] prerequisites = {{1, 0}, {0, 1}, {3, 1}, {3, 2}};
        System.out.println(canFinish.canFinish(numCourses, prerequisites));
    }
}
