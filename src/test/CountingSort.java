package test;

public class CountingSort {

    public static void countingSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int max = arr[0];
        for(int num : arr) {
            if(num > max) max = num;
        }

        int[] nums = new int[max+1];
        for(int i=0; i<arr.length; i++){
            nums[arr[i]]++;
        }
        int p = 0;
        for(int i=0; i<nums.length; i++){
            while(nums[i] > 0){
                arr[p++] = i;
                nums[i]--;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 2, 2, 8, 3, 3, 1};
        countingSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
