package s8_tree;

/**
 * 124. 二叉树中的最大路径和
 */
public class Hot124_maxPathSum {

    // 记录最大路径
    private int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

    public int maxGain(TreeNode node){
        if (node == null) return 0;

        int leftPath = Math.max(maxGain(node.left), 0);
        int rightPath = Math.max(maxGain(node.right), 0);

        // 更新最大路径，以当前node为根节点的路径：左子树->根节点->右子树
        int newPath = node.val + leftPath + rightPath;
        maxSum = Math.max(maxSum, newPath);

        // 返回节点的最大贡献值，只能选择左子树或右子树
        return node.val + Math.max(leftPath, rightPath);
    }

    public static void main(String[] args) {
        //       -10
        //       /  \
        //      9   20
        //         /  \
        //        15   7
        //
        // 最大路径：15 → 20 → 7 = 42

        TreeNode root = new TreeNode(-10);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        int result = new Hot124_maxPathSum().maxPathSum(root);
        System.out.println("最大路径和: " + result); // 期望：42
    }
}
