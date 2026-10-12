package sort;

import java.util.Arrays;

/**
 * 计数排序
 */
public class CountingSort {

    public static void countingSort(int[] arr) {
        if(arr == null || arr.length < 2) return;

        // 1. 找到数组中的最大值
        int max = arr[0];
        for(int num : arr){
            if(num > max) max = num;
        }

        // 2. 创建计数数组
        int[] count = new int[max+1];

        // 3. 统计每个元素出现的次数
        for(int num : arr){
            count[num]++;
        }

        // 4. 根据计数数组，将元素放回原数组（稳定排序）
        int index = 0;
        for(int i=0; i<count.length; i++){
            while(count[i] > 0){
                arr[index++] = i;
                count[i]--;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 2, 2, 8, 3, 3, 1};
        countingSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
