package com.searchDemo;

/**
 * @Auhtor：zhuminming
 * @Date:2017-10-23 22:36
 */
public class CommonsearchImpl {

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

    public static void main(String[] args){
        int[] arr={48,27,13,76,97,65,38,49};
        CommonsearchImpl search =new CommonsearchImpl();
        System.out.println(".........................BinarySearch.......................");
        BinarySearch binarySearch = search.new BinarySearch();
        System.out.println(binarySearch.binarySearch(arr, 27));
    }
}
