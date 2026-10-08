package s14_greedy;

/**
 * 45. 跳跃游戏 II
 */
public class Hot45_jump {

    public int jump(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;

        int count = 0;
        int maxPosition = 0;
        int end = 0;

        for (int i = 0; i < n - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {
                count++;
                end = maxPosition;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Hot45_jump solution = new Hot45_jump();
        int[] nums = {2, 3, 1, 1, 4};
        int result = solution.jump(nums);
        System.out.println(result);
    }
}
