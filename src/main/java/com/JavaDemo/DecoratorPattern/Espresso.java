package com.JavaDemo.DecoratorPattern;

/**
 * @Author: zhuminming
 * @create: 2018/3/31 13:30
 * @GitHubAddress: https://github.com/zhuminming
 */
public class Espresso extends Beverage{
    @Override
    public double cost() {
        return 1.99;
    }

    public Espresso(){
        description="Espresso";
    }
}
