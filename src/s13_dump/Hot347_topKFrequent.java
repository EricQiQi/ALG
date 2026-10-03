package s13_dump;

import java.util.*;

/**
 * 347. 前 K 个高频元素
 */
public class Hot347_topKFrequent {

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            heap.offer(new int[]{entry.getKey(), entry.getValue()});
            if(heap.size() > k){
                heap.poll();
            }
        }

        int[] res = new int[k];
        for(int i=0; i<k; i++){
            res[i] = heap.poll()[0];
        }
        return res;
    }

    public static void main(String[] args) {
        Hot347_topKFrequent solution = new Hot347_topKFrequent();
        int[] nums = {1, 1, 1, 2, 2, 3};
        int[] res = solution.topKFrequent(nums, 2);
        System.out.println(Arrays.toString(res));
    }
}
