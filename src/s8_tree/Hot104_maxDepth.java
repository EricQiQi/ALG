package s8_tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 104. 二叉树的最大深度
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 * 说明: 叶子节点是指没有子节点的节点。
 *
 */
public class Hot104_maxDepth {

    /**
     * 方法1：递归，深度优先遍历
     * 时间复杂度：O(n)
     * 空间复杂度：O(h)
     */
    public int maxDepth(TreeNode root){
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }


    /**
     * 方法2：迭代，广度优先遍历
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public int maxDepth_2(TreeNode root){
        if(root == null) return 0;
        int maxDepth = 0;

        Deque<TreeNode> dq = new ArrayDeque<>();
        dq.offerLast(root);
        while(!dq.isEmpty()){
            int size = dq.size();
            while(size > 0){
                TreeNode node = dq.pollFirst();
                if(node.left != null){
                    dq.offerLast(node.left);
                }
                if(node.right != null){
                    dq.offerLast(node.right);
                }
                size--;
            }
            maxDepth++;
        }
        return maxDepth;
    }

    public static void main(String[] args) {
        //       3
        //      / \
        //     9   20
        //        /  \
        //       15   7
        Hot104_maxDepth solution = new Hot104_maxDepth();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println("最大深度为： " + solution.maxDepth(root));

        System.out.println("最大深度为： " + solution.maxDepth_2(root));
    }
}
