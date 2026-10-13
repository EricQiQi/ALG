package test;

public class ShellSort {

    public static void shellSort(int[] arr) {
        if(arr == null || arr.length < 2) return;
        int n=arr.length;
        for(int gap = n/2; gap>0; gap/=2){
            for(int i=gap; i<n; i++){
                int temp = arr[i];
                int j = i-gap;
                while(j>=0 && arr[j] > temp){
                    arr[j+gap] = arr[j];
                    j = j-gap;
                }
                arr[j+gap] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        shellSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
