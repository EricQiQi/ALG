package s8_tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 102. 二叉树的层序遍历
 *
 * 给你二叉树的根节点 root ，返回其节点值 层序遍历 结果。
 *
 */
public class Hot102_levelOrder {

    /**
     * 层序遍历：BFS（广度优先搜索）
     * 核心：用队列逐层遍历，每轮用 size 锁定当前层的节点数
     *
     * @param root 根节点
     * @return 每层节点值的列表
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            // 关键：提前锁定当前层的节点数，避免遍历时 queue.size() 变化
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                // 下一层的节点入队（左先右后）
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            res.add(level);
        }
        return res;
    }

    public static void main(String[] args) {
        //        3
        //       / \
        //      9   20
        //         /  \
        //        15   7
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        Hot102_levelOrder solution = new Hot102_levelOrder();
        List<List<Integer>> res = solution.levelOrder(root);
        System.out.println("层序遍历: " + res);  // [[3], [9, 20], [15, 7]]
    }
}
