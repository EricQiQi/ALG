package s14_greedy;

/**
 * 55. 跳跃游戏
 */
public class Hot55_canJump {

    /**
     * 贪心算法
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public boolean canJump(int[] nums) {
        int n = nums.length;
        int maxStep = 0;

        for(int i=0; i<n; i++){
            if(i > maxStep){
                return false;
            }

            maxStep = Math.max(maxStep, i + nums[i]);
            if(maxStep>=n-1){
                return true;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Hot55_canJump solution = new Hot55_canJump();
        int[] nums = {2, 3, 1, 1, 4};
        boolean result = solution.canJump(nums);
        System.out.println(result); // 输出: true
    }
}
