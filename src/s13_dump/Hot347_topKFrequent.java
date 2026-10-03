package s13_dump;

import java.util.*;

/**
 * 347. 前 K 个高频元素
 */
public class Hot347_topKFrequent {

    /**
     * 方法1：HashMap 统计频率 + 小顶堆挑 Top K
     * 堆按频率排序（a[1]-b[1]），容量固定为 k，超过就弹掉堆顶（频率最小的），
     * 最终堆里留下的就是频率最高的 k 个
     * <p>
     * 时间复杂度：O(nlogk)
     * 空间复杂度：O(n)
     */
    public int[] topKFrequent_1(int[] nums, int k) {
        // 1. 统计频率：元素 -> 出现次数
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        // 2. 小顶堆按频率排序，堆里存 {元素, 频率} 二元组
        //    每次 offer 后若超过 k 个就 poll 弹掉堆顶（当前频率最小的）
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            heap.offer(new int[]{entry.getKey(), entry.getValue()});
            if (heap.size() > k) {
                heap.poll();
            }
        }

        // 3. 收割：poll 出的二元组取 [0]（元素值），题目允许任意顺序
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = heap.poll()[0];
        }
        return res;
    }

    /**
     * 方法2：HashMap 统计频率 + 桶排序
     * 频率最大不超过 n，用下标当频率建桶，再从高频桶往低频桶收集 k 个
     * <p>
     * 时间复杂度：O(n)（唯一满足进阶要求的解法）
     * 空间复杂度：O(n)
     */
    public int[] topKFrequent_2(int[] nums, int k) {
        // 1. 统计频率：元素 -> 出现次数
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        // 2. 建桶：下标 = 频率，桶里装该频率对应的所有元素
        //    频率范围 [0, n]，故桶数组开 n+1 长
        List<Integer>[] bucket = new List[nums.length + 1];
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            int num = entry.getKey();
            int count = entry.getValue();

            if(bucket[count] == null){
                bucket[count] = new ArrayList<>();
            }
            bucket[count].add(num);
        }

        // 3. 从高频桶（下标大）往低频桶收集，凑够 k 个即停
        int[] res = new int[k];
        int index = 0;
        for(int i=bucket.length-1; i>=0 && index < k; i--){
            if(bucket[i] != null){
                for(int num : bucket[i]){
                    res[index++] = num;
                    if(index == k) break;   // 同一桶内可能超过 k，装满就停
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        Hot347_topKFrequent solution = new Hot347_topKFrequent();
        int[] nums = {1, 1, 1, 2, 2, 3};
        int[] res_1 = solution.topKFrequent_1(nums, 2);
        System.out.println(Arrays.toString(res_1));
        int[] res_2 = solution.topKFrequent_2(nums, 2);
        System.out.println(Arrays.toString(res_2));
    }
}
