package s8_tree;

/**
 * 236. 二叉树的最近公共祖先
 *
 */
public class Hot236_lowestCommonAncestor {
    /**
     * 236. 二叉树的最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 如果root为空或root为p或q中的任意一个，则返回root
        if(root == null || root == p || root == q) return root;

        // 递归遍历左子树，寻找p或q
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        // 递归遍历右子树，寻找p或q
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 如果left和right都为空，说明root的左/右子树中都不包含p或q，返回null
        if (left != null && right != null) return root;
        // 如果left为空，说明p或q不存在于root的左子树中，返回right
        // 如果right为空，说明p或q不存在于root的右子树中，返回left
        return left != null ? left : right;
    }

    public static void main(String[] args) {
        //          3
        //         / \
        //        5   1
        //       / \ / \
        //      6  2 0  8
        //        / \
        //       7   4
        TreeNode root = new TreeNode(3);
        TreeNode node5 = new TreeNode(5);
        TreeNode node1 = new TreeNode(1);
        root.left = node5;
        root.right = node1;
        node5.left = new TreeNode(6);
        TreeNode node2 = new TreeNode(2);
        node5.right = node2;
        node1.left = new TreeNode(0);
        node1.right = new TreeNode(8);
        node2.left = new TreeNode(7);
        TreeNode node4 = new TreeNode(4);
        node2.right = node4;

        Hot236_lowestCommonAncestor solution = new Hot236_lowestCommonAncestor();

        // 测试1：p=5, q=1 → LCA=3（分别在根节点两侧）
        TreeNode lca1 = solution.lowestCommonAncestor(root, node5, node1);
        System.out.println("5 和 1 的最近公共祖先: " + lca1.val); // 期望：3

        // 测试2：p=5, q=4 → LCA=5（p 本身就是 q 的祖先）
        TreeNode lca2 = solution.lowestCommonAncestor(root, node5, node4);
        System.out.println("5 和 4 的最近公共祖先: " + lca2.val); // 期望：5
    }
}
