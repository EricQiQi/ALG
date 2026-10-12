package s17_skill;

/**
 * 136. 只出现一次的数字
 */
public class Hot136_singleNumber {

    public int singleNumber(int[] nums) {
        int res = 0;
        for (int num : nums) {
            // 异或运算，相同为0，不同为1
            res ^= num;
        }
        return res;
    }

    public static void main(String[] args) {
        Hot136_singleNumber hot136_singleNumber = new Hot136_singleNumber();
        int[] nums = {2, 2, 1};
        int i = hot136_singleNumber.singleNumber(nums);
        System.out.println(i);
    }
}
