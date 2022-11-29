package com.tongfu.dao;

import com.tongfu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Mapper
@Repository
public class UsersDao {


    private static Map<Integer,User> users = null;

    static {
        users    = new HashMap<>( );
        users.put(1001,new User(1101,"admin","1111","111","111","1111",0,"",new Date(),new Date()));
        users.put(1002,new User(1002,"wangshuagnrui","1111","111","111","1111",1,"",new Date(),new Date()));
        users.put(1003,new User(1003,"zhangfei","1111","111","111","1111",0,"",new Date(),new Date()));
        users.put(1004,new User(1004,"lisi","1111","111","111","1111",1,"",new Date(),new Date()));
        users.put(1005,new User(1005,"wangwu","1111","111","111","1111",0,"",new Date(),new Date()));
        users.put(1006,new User(1006,"zhaoliu","1111","111","111","1111",1,"",new Date(),new Date()));

    }

    private static Integer initId = 1007;

    public void save(User user){
        if (user.getUserId()==null){
            user.setUserId(initId++);
        }
    }


    public Collection<User> getAll(){
        return users.values();
    }

    public User get(Integer userId){
        return users.get(userId);
    }

    public void delete(Integer userId){
        users.remove(userId);
    }


}