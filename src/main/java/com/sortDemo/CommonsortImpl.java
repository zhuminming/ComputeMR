package com.sortDemo;

/**
 * Created by zmm on 2017-09-04.
 */
public class CommonsortImpl {
    /*
     * 冒泡排序实现
     * 时间复杂度为 O(n^2)
     * */
    class BubbleSort{
        public void bubbleSort(int[] arr){
            if(arr.length==0||arr==null) return;
            for(int i=1 ;i<arr.length;i++){
                for(int j=0;j<arr.length-i;j++){
                    if(arr[j]>arr[j+1]){
                       int temp = arr[j];
                        arr[j]=arr[j+1];
                        arr[j+1]=temp;
                    }
                }
            }
            println(arr);
        }
    }

    /*
     * 选择排序实现
     * 时间复杂度为 O(n^2)
     * */
    class SelectionSort{
        public void selectionSort(int[] arr){
            if(arr.length==0||arr==null) return;
            for(int i =0;i<arr.length-1;i++ ){
                int minSign=i;   //minSign为每次要获取最小数的数组下标
                for(int j =i+1;j<arr.length;j++){
                    if(arr[minSign]>arr[j]){
                        minSign=j;
                    }
                }
                //交换数据
                int temp=arr[minSign];
                arr[minSign]=arr[i];
                arr[i]=temp;
            }
            println(arr);
        }
    }

    /*
     *插入排序实现
     * 时间复杂度为O(n^2)
     *  */
    class InsertSort{
        public void insertSort(int[] arr){
            if(arr.length==0||arr==null) return;
            for(int i=1;i<arr.length;i++) {
                int j = i;
                int temp = arr[i];
                //从后往前比较
                while(j>0&&temp<arr[j-1]){
                    arr[j]=arr[j-1];
                    j--;
                }
                arr[j]=temp;
            }
            println(arr);
        }
    }
    /*
     *快速排序
     * 时间复杂度为O(nlogn)
     * */
    class QuickSort{
        private void quickSort(int[] arr){
            if(arr.length==0||arr==null) return;
            int left =0;
            int right=arr.length-1;
            quickSort(arr, left, right);
            println(arr);
        }
        private void quickSort(int[] arr,int left ,int right){
            if(left>right) return;
            int middle = partition(arr, left, right);
            quickSort(arr,left ,middle-1);
            quickSort(arr,middle+1 ,right);
        }


       /*
         *1. 从数列中挑出一个元素作为基准数。
         *2. 分区过程，将比基准数大的放到右边，小于或等于它的数都放到左边。
         *3. 再对左右区间递归执行第二步，直至各区间只有一个数。
         */
        private  int partition(int[] arr,int left ,int right){
            int temp=arr[left];
            while(left<right){
                //从右向左遍历
                while(left<right&&temp<=arr[right]){
                    right--;
                }
                //交换
                arr[left]=arr[right];
                //从左向右遍历
                while(left<right&&temp>=arr[left]){
                    left++;
                }
                //交换
                arr[right]=arr[left];
            }
            arr[left]=temp;     //left与right值相等，指向同一个下标
            return left;
        }
    }

    /*
     *hash排序
     * */
    class HashInsertSort{
        public void hashInsertSort(int[] arr){
            int gap = arr.length;
            while(gap>1){
                gap=sort(arr,gap);
            }
            println(arr);
        }

        private int sort(int[] arr,int length){
            int gap= (int) length/2;
            for(int i = gap ;i<arr.length;){
                int temp = arr[i];
                int j = i;
                while(j>0&&temp<arr[j-gap]){
                    arr[j]=arr[j-gap];
                    j-=gap;
                }
                arr[j]=temp;
                i+=gap;
            }
            return gap;
        }
    }

    /*
     *堆排序
     * 时间复杂度为O(nlogn)
     * */
    class HeapSort{

        public void heapSort(int[] arr){
            int middle=(int) arr.length/2-1;
            int length=arr.length;
            int last = length-1;
            for(int i =0 ; i< length ;i++){
                generateMaxHeapSore(arr,length-i);
                int max = arr[0];
                arr[0]=arr[last-i];
                arr[last-i]=max;
                System.out.print(arr[last-i]);
            }

        }

        /*
        * 生成大顶堆
        * */
        private void generateMaxHeapSore(int[] arr,int length){
            int i=(int) length/2-1;
            for(;i>=0;i--){
                adjustMaxHeapSore(arr,length,i);
            }
        }

        /*
        * 调整大顶堆
        * */
        private void adjustMaxHeapSore(int[] arr,int length ,int i){
            int left = getLeft(i);
            int right = getRight(i);

            int max=i;
            if(left<length&&arr[left]>arr[i]){
                max = left;
            }
            if(right<length&&arr[right]>arr[i]&&arr[right]>arr[left]){
                max = right;
            }
            if(max!=i){
                int temp = arr[i];
                arr[i]=arr[max];
                arr[max]=temp;
                //被交换的位置以前是大顶堆，现在可能不是大根堆所以需要重新调整使其成为大根堆结构
                adjustMaxHeapSore(arr,length,max);
            }
        }

        private int getLeft(int i ){
            return 2*i+1;//i的左孩子节点序号
        }

        private int getRight(int i ){
            return 2*i+2;//i的右孩子节点序号
        }
    }



    /*
     * 归并排序
     * 时间复杂度为O(nlogn)
     * */
    class MergeSort{

        public  void mergeSort(int[] arr){
            int[] temp = new int[arr.length];
            merge_Sort(arr, 0, arr.length - 1, temp);
            println(arr);

        }

        //划分后的序列段两两排序合并
        private void merge_Sort(int[] arr,int left , int mid , int last , int[] temp){
            int i= left;
            int j = mid+1;
            int t =0;
            while(i<=mid&&j<=last){
                if(arr[i]>arr[j]){
                    temp[t++]=arr[j++];
                }else if(arr[i]<arr[j]){
                    temp[t++]=arr[i++];
                }
            }

            //将左序列剩余元素填充进temp中
            while(i<=mid){
                temp[t++]=arr[i++];
            }

            //将右序列剩余元素填充进temp中
            while(j>mid&&j<=last){
                temp[t++]=arr[j++];
            }

            t=0;
            //将temp中的元素全部拷贝到原数组中
            while(left<=last){
                arr[left++]=temp[t++];
            }

        }


        //序列每次折半拆分
        private void merge_Sort(int[] arr,int left , int last , int[] temp){
            if(left<last){
                int mid = (int) (left+last)/2;
                merge_Sort(arr, left, mid, temp);  //左边归并排序，使得左子序列有序
                merge_Sort(arr, mid+1, last, temp);  //右边归并排序，使得右子序列有序
                merge_Sort(arr, left,mid, last, temp); //将两个有序子数组合并操作
            }
        }
    }

    class RadixSort{
        public void radix_sort(int[] arr){

        }
    }
    private void println(int[] arr){
        for(int i : arr){
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println();
    }

    public static void main(String[] args){
        int[] arr={48,27,13,76,97,65,38,49};
        CommonsortImpl sort =new CommonsortImpl();
        BubbleSort bubbleSort = sort.new BubbleSort();
        System.out.println(".........................BubbleSort.......................");
        bubbleSort.bubbleSort(arr);
        System.out.println(".........................SelectionSort.......................");
        SelectionSort selectionSort = sort.new SelectionSort();
        selectionSort.selectionSort(arr);
        System.out.println(".........................InsertSort.......................");
        InsertSort insertSort = sort.new InsertSort();
        insertSort.insertSort(arr);
        System.out.println(".........................QuickSort.......................");
        QuickSort quickSort = sort.new QuickSort();
        quickSort.quickSort(arr);
        System.out.println(".........................HashInsertSort.......................");
        HashInsertSort hashInsertSort = sort.new HashInsertSort();
        hashInsertSort.hashInsertSort(arr);
        System.out.println(".........................HeapSort.......................");
        HeapSort heapSort = sort.new HeapSort();
        System.out.println(".........................MergeSort.......................");
        MergeSort mergeSort = sort.new MergeSort();
        mergeSort.mergeSort(arr);

    }
}
