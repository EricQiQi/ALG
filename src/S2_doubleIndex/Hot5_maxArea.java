package S2_doubleIndex;

/**
 *
 * 盛最多水的容器
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 *
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 返回容器可以储存的最大水量。
 *
 * 说明：你不能倾斜容器。
 *
 */
public class Hot5_maxArea {
    public int maxArea(int[] height) {
        int i=0;
        int j=height.length-1;
        int res=0;
        while(i < j){
            // 取高
            int h = Math.min(height[i], height[j]);
            res = Math.max(res, h*(j-i));

            // 找到短板，移动短板的指针
            if(height[i] < height[j]){
                i++;
            }else{
                j--;
            }

        }
        return res;
    }

    public static void main(String[] args) {
        Hot5_maxArea hot5_maxArea = new Hot5_maxArea();
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(hot5_maxArea.maxArea(height));
    }
}
