package com.searchDemo;

/**
 * @Auhtor：zhuminming
 * @Date:2017-10-23 22:36
 */
public class CommonsearchImpl {
    /*
     * 二分查找（折半查找）
     * 时间复杂度为O(log2n)
     * */
    class BinarySearch{
        public int binarySearch(int[] arr, int  value){
            return binarySearch(arr,value,0,arr.length-1);
        }

        private int binarySearch(int[] arr, int  value,int left , int right){
            int mid = (int)(right-left)/2;
            if(arr[mid]<value){
                return  binarySearch(arr,value,left,mid);
            }else if(arr[mid]>value){
                return  binarySearch(arr,value,mid+1,right);
            }else if(arr[mid]==value){
                return mid;
            }
            return -1;
        }
    }
    /*
     * 插入查找（折半查找）
     * 时间复杂度为O(log2n)
     * */
    class InsertSearch{
        public void insertSearch(int[] arr, int  value){
            int length = arr.length;
            insertSearch(arr,value,0,length-1);

        }
        public int insertSearch(int[] arr, int  value,int low,int high){
            int mid  = low + (value-arr[low])/arr[high]-arr[low]*(high-low);
            if(arr[mid]>value){
                return insertSearch(arr,value,0,mid-1);
            }else if(arr[mid]<value){
                return insertSearch(arr,value,mid+1,high);
            }else if(arr[mid]==value){
                return mid;
            }
            return -1;
        }
    }

    public static void main(String[] args){
        int[] arr={48,27,13,76,97,65,38,49};
        CommonsearchImpl search =new CommonsearchImpl();
        System.out.println(".........................BinarySearch.......................");
        BinarySearch binarySearch = search.new BinarySearch();
        System.out.println(binarySearch.binarySearch(arr, 27));
    }
}
