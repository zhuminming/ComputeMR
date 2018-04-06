package com.JavaDemo.DecoratorPattern;

/**
 * @Author: zhuminming
 * @create: 2018/3/31 14:04
 * @GitHubAddress: https://github.com/zhuminming
 */
public class Whip extends CondimentDecorator{
    Beverage beverage;
    public Whip(Beverage beverage){
        this.beverage =beverage;
    }
    @Override
    public String getDescription() {
        return beverage.getDescription()+",Whip";
    }

    @Override
    public double cost() {
        return 0.30+beverage.cost();
    }
}
