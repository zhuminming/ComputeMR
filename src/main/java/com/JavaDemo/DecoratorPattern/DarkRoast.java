package com.JavaDemo.DecoratorPattern;

/**
 * @Author: zhuminming
 * @create: 2018/3/31 13:53
 * @GitHubAddress: https://github.com/zhuminming
 */
public class DarkRoast extends Beverage{
    @Override
    public double cost() {
        return 0.23;
    }

    public DarkRoast(){
        description ="DarkRoast";
    }
}
