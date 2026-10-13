package sort;

/**
 * 选择排序
 */
public class SelectionSort {

    /**
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    public static void selectionSort(int[] arr){
        if(arr == null || arr.length < 2) return;

        int n = arr.length;
        for(int i=0; i<n-1; i++){
            int min = i;
            for(int j=i+1; j<n; j++){
                if(arr[j] < arr[min]){
                    min = j;
                }
            }

            if(min != i){
                swap(arr, i, min);
            }
        }
    }

    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        selectionSort(array);
        for (int i : array) {
            System.out.print(i + " ");
        }
    }
}
