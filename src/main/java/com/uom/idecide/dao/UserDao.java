package com.uom.idecide.dao;

import com.uom.idecide.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * 数据访问接口
 * @author Administrator
 *
 */
/*
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
    public User findByEmail(String email);
}
*/

public interface UserDao extends MongoRepository<User,String> {

    public User findByEmail(String email);


}
