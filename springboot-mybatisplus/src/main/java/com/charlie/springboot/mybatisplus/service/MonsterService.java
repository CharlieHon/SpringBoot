package com.charlie.springboot.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charlie.springboot.mybatisplus.bean.Monster;

/**
 * 1. 传统方式：在接口中定义/声明方法，然后在实现类中进行实现
 * 2. Mybatis-Plus：可以继承父接口 IService
 * 3. IService接口声明了很多方法，比如crud
 * 4. 如果默认提供的方法不能满足需求，可以在接口中再声明需要的方法，然后在实现类中实现即可
 */
public interface MonsterService extends IService<Monster> {
    // 自定义方法
    //public void t1();
}
