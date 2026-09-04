package test;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {

    public static void quickSort(int[] arr){
        if (arr == null || arr.length == 0) return;
        quickSort(arr, 0, arr.length-1);
    }

    public static void quickSort(int[] arr, int left, int right){
        if (left >= right) return;
        int pivotIndex = partition(arr, left, right);
        quickSort(arr, left, pivotIndex-1);
        quickSort(arr, pivotIndex+1, right);
    }

    public static int partition(int[] arr, int left, int right){
        int pivotIndex = left + new Random().nextInt(right-left);
        swap(arr, left, pivotIndex);
        int pivot = arr[left];
        int l = left;
        int r = right;
        while(l < r){
            while (l < r && arr[r] >= pivot) r--;
            while (l < r && arr[l] <= pivot) l++;
            if (l<r) swap(arr, l, r);
        }
        swap(arr, left, l);
        return l;
    }

    public static void swap(int[] arr, int l, int r){
        int t = arr[l];
        arr[l] = arr[r];
        arr[r] = t;
    }
    public static void main(String[] args) {
//        int[] arr = {3, 1, 2};
        int[] arr = {3, 6, 8, 10, 1, 2, 1};

        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
