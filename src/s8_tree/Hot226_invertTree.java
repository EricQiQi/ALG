package s8_tree;

public class Hot226_invertTree {

    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
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
