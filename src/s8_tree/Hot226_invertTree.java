package s8_tree;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 226. 翻转二叉树
 */
public class Hot226_invertTree {

    /**
     * 方法1：递归
     * 时间复杂度：O(n)
     * 空间复杂度：O(h)
     */
    public TreeNode invertTree_1(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTree_1(root.left);
        TreeNode right = invertTree_1(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    /**
     * 方法2：BFS
     * 时间复杂度：O(n)
     * 空间复杂度：O(h)
     */
    public TreeNode invertTree_2(TreeNode root) {
        if (root == null) return null;
        Deque<TreeNode> dq = new ArrayDeque<>();
        dq.offerLast(root);
        while(!dq.isEmpty()){
            TreeNode node = dq.pollFirst();
            TreeNode left = node.left;
            TreeNode right = node.right;
            node.left = right;
            node.right = left;
            if (left != null) dq.offerLast(left);
            if (right != null) dq.offerLast(right);
        }
        return root;
    }


    public static void main(String[] args) {
        //       4
        //      / \
        //     2   7
        //    / \ / \
        //   1  3 6  9
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);

        Hot102_levelOrder solution = new Hot102_levelOrder();
        System.out.println("翻转前：" + solution.levelOrder(root));

        Hot226_invertTree hot226InvertTree = new Hot226_invertTree();
        TreeNode invertedRoot = hot226InvertTree.invertTree(root);

        System.out.println("翻转后：" + solution.levelOrder(root));

    }
}
