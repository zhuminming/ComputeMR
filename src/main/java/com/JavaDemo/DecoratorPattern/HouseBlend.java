package com.JavaDemo.DecoratorPattern;

/**
 * @Author: zhuminming
 * @create: 2018/3/31 13:37
 * @GitHubAddress: https://github.com/zhuminming
 */
public class HouseBlend extends Beverage{
    @Override
    public double cost() {
        return 0.89;
    }

    public HouseBlend(){
        description="HouseBlend";
    }
}
