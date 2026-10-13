package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序
 */
public class BucketSort {

    public static void bucketSort(double[] arr) {
        if (arr == null || arr.length < 2) return;
        int n = arr.length;

        List<List<Double>> buckets = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            buckets.add(new ArrayList<>());
        }

        for (double num : arr) {
            int index = (int) (num * n);
            buckets.get(index).add(num);
        }

        for(List<Double> bucket : buckets){
            Collections.sort(bucket);
        }

        int idx = 0;
        for(List<Double> bucket : buckets){
            for(double num : bucket){
                arr[idx++] = num;
            }
        }
    }

    public static void main(String[] args) {
        double[] arr = {0.42, 0.32, 0.33, 0.52, 0.37, 0.47, 0.51};
        bucketSort(arr);
        for (double v : arr) {
            System.out.print(v + " ");
        }
    }

}
