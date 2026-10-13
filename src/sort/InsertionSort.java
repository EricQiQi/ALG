package sort;


/**
 * 插入排序
 */
public class InsertionSort {

    /**
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    public static void insertionSort(int[] arr) {
        if(arr == null || arr.length < 2) return;

        int n = arr.length;
        for(int i=1; i<n; i++){
            int key = arr[i];
            int j = i-1;
            while(j >= 0 && arr[j] > key){
                arr[j+1] = arr[j];
                j--;
            }

            arr[j+1] = key;
        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        insertionSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
