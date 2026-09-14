package test;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class BubbleSort {
    public static void bubble(int[] arr){
        if(arr == null || arr.length == 0) return;

        int n=arr.length;
        for(int i=0; i<n-1; i++){
            Boolean swapped = false;
            for(int j=0; j<n-1-i; j++){
                if(arr[j] > arr[j+1]){
                    swap(arr, j, j+1);
                    swapped = true;
                }
            }
            if(!swapped) break;
        }
    }

    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        bubble(arr);
        for(int i: arr){
            System.out.print(i+" ");
        }
    }
}
