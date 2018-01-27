package com.JavaDemo.classloading.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhuminming
 * @create: 2018/1/17 22:52
 * @GitHubAddress: https://github.com/zhuminming
 */
public class GenericsAndCovariance {
    public static void main(String[] args){
        List<Apple> lists = new ArrayList<Apple>();
        lists.add(new Apple());
        lists.add(null);

    }
}
