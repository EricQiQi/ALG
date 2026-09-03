package s8_tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 199. 二叉树的右视图
 */
public class Hot199_rightSideView {

    /**
     * 方法1：广度优先搜索
     *
     * @param root
     * @return
     */
    public static List<Integer> rightSideView_1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);

                if (i == size - 1) {
                    res.add(node.val);
                }
            }
        }
        return res;
    }


    /**
     * 方法2：深度优先搜索
     * 遍历顺序，根->右->左
     * 时间复杂度：O(n)
     * 空间复杂度：O(h)
     * @param root
     * @return
     */
    public static List<Integer> rightSideView_2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        rightOrder(root, res, 1);
        return res;
    }

    public static void rightOrder(TreeNode root, List<Integer> res, int depth) {
        if (root == null) return;
        if (depth > res.size()){
            res.add(root.val);
        }
        rightOrder(root.right, res, depth+1);
        rightOrder(root.left, res, depth+1);
    }

    public static void main(String[] args) {
        //        1
        //       / \
        //      2   3
        //       \   \
        //        5   4
        TreeNode root = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(5)),
                new TreeNode(3, null, new TreeNode(4)));
        System.out.println("广度优先遍历BFS: " + rightSideView_1(root));
        System.out.println("深度优先遍历DFS: " + rightSideView_2(root));
    }
}
