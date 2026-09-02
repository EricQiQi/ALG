package s8_tree;

/**
 * 543. 二叉树的直径
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点问最长路径的长度。这条路径可能穿过根结点。
 * 二叉树的直径：任意两个节点之间的最长路径
 */
public class Hot543_diameterOfBinaryTree {

    // 记录节点数，直径=节点数-1
    int ans;

    /**
     * 计算二叉树的直径
     * @param root
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        ans = 1;
        depth(root);

        // 返回直径=节点数-1
        return ans - 1;
    }

    /**
     * 计算二叉树的深度
     * @param node
     * @return
     */
    public int depth(TreeNode node){
        // 如果节点为空，返回深度0
        if(node == null) return 0;
        // 计算左子树的深度
        int L = depth(node.left);
        // 计算右子树的深度
        int R = depth(node.right);

        // 更新最大直径：当前节点的深度= L + R + 1
        ans = Math.max(ans, L + R + 1);

        // 返回当前节点的深度
        return Math.max(L, R) + 1;
    }

    public static void main(String[] args) {
        //        1
        //       / \
        //      2   3
        //     / \
        //    4   5
        // 最长路径：4→2→1→3，直径 = 3
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        Hot543_diameterOfBinaryTree solution = new Hot543_diameterOfBinaryTree();
        System.out.println("直径: " + solution.diameterOfBinaryTree(root));  // 3
    }
}
