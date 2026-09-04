package s8_tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * 105. 从前序与中序遍历序列构造二叉树
 * 根据一棵树的前序遍历与中序遍历构造二叉树。
 *
 */
public class Hot105_buildTree_2 {

    // pre: 前序遍历的全局指针，指向当前要作为根节点的元素
    // in:  中序遍历的全局指针，用于判断当前子树边界
    private int pre;
    private int in;

    /**
     * 方法2：全局指针 + stop 值隐式划分子树边界
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * <p>
     * 核心思想：
     * 前序遍历的第一个节点是根，在中序遍历中，根节点左边是左子树，右边是右子树。
     * 用 stop 值标记"当前子树在中序遍历中的右边界"，当 inorder[in] == stop 时，
     * 说明当前子树已全部构建完毕，返回 null。
     * <p>
     * 以 preorder=[3,9,20,15,7], inorder=[9,3,15,20,7] 为例：
     * <p>
     * dfs(stop=MAX) → 根=3
     * 左子树 dfs(stop=3)  → 根=9, 左=null(in[0]==9==stop), 右=null
     * in++  → 跳过中序中的 3
     * 右子树 dfs(stop=MAX) → 根=20
     * 左子树 dfs(stop=20) → 根=15, 左=null(in[2]==15==stop), 右=null
     * in++ → 跳过中序中的 20
     * 右子树 dfs(stop=MAX) → 根=7, 左=null(in[4]==7==stop), 右=null
     * <p>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        pre = 0;
        in = 0;
        return dfs(preorder, inorder, Integer.MAX_VALUE);
    }

    /**
     * @param stop 当前子树在中序遍历中的右边界值。
     *             当 inorder[in] == stop 时，说明当前子树构建完毕，返回 null。
     *             - 构建左子树时，stop = 当前根节点的值（中序中根是左子树的右边界）
     *             - 构建右子树时，stop = 父级传下来的 stop（右子树的边界和父级相同）
     */
    private TreeNode dfs(int[] preorder, int[] inorder, int stop) {
        // 前序遍历用完了，返回空
        if (pre >= preorder.length) return null;
        // 中序遍历当前位置等于 stop，说明当前子树边界已到，返回空
        if (inorder[in] == stop)
            return null;
        // 前序遍历当前元素就是根节点
        TreeNode root = new TreeNode(preorder[pre++]);
        // 构建左子树：左子树在中序中的右边界就是当前根节点的值
        root.left = dfs(preorder, inorder, root.val);
        // 左子树构建完毕，中序指针跳过根节点（进入右子树区间）
        in++;
        // 构建右子树：右子树的边界继承自父级的 stop
        root.right = dfs(preorder, inorder, stop);
        return root;
    }


    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};

        TreeNode root = new Hot105_buildTree_2().buildTree(preorder, inorder);

        // 层序遍历验证结果
        if (root == null) {
            System.out.println("buildTree 方法尚未实现");
            return;
        }
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        // 期望输出：3 9 20 15 7
    }
}
