package com.Java.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

/**
 * @Author: zhuminming
 * @create: 2018/1/13 11:40
 * @GitHubAddress: https://github.com/zhuminming
 */
public class TestDemo {
    public static void main(String[] args){
        Set<Integer> sets = Sets.newHashSet();
        List<Integer> lists = Lists.newArrayList();
        for(int i = -3 ; i<3 ; i++){
            sets.add(i);
            lists.add(i);
        }

        for(int i = 0 ; i<3 ; i++){
//            sets.remove(i);
            lists.remove(i);
        }

        System.out.println(sets+"--"+lists);

    }
}
