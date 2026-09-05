package s2_doubleIndex;

/**
 *
 * 5.盛最多水的容器
 *
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 *
 */
public class Hot11_maxArea {

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public int maxArea(int[] height) {
        int i=0;
        int j=height.length-1;
        int res=0;
        while(i < j){
            // 取高
            int h = Math.min(height[i], height[j]);
            // 注意 底的取值 !!!
            int len = j-i;
            res = Math.max(res, h*len);

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
        Hot11_maxArea hot11_maxArea = new Hot11_maxArea();
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(hot11_maxArea.maxArea(height));
    }
}
