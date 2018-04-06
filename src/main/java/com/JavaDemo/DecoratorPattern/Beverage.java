package com.JavaDemo.DecoratorPattern;

/**
 * @Author: zhuminming
 * @create: 2018/3/31 13:26
 * @GitHubAddress: https://github.com/zhuminming
 */
public abstract class Beverage {
    String description ="Unknown Beverage";

    public String getDescription(){
        return description;
    }

    public abstract double cost();
}
