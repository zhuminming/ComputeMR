package com.JavaDemo.DecoratorPattern;

/**
 * @Author: zhuminming
 * @create: 2018/3/31 13:39
 * @GitHubAddress: https://github.com/zhuminming
 */
public class Mocha extends CondimentDecorator{
    Beverage beverage;
    public Mocha(Beverage beverage){
        this.beverage=beverage;
    }
    @Override
    public String getDescription() {
        return beverage.getDescription()+",Mocha";
    }

    @Override
    public double cost() {
        return 0.20+beverage.cost();
    }
}
