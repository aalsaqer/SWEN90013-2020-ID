package com.uom.idecide.dao;

import com.uom.idecide.pojo.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
/*
public interface AdminDao extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin>{
    public Admin findByEmail(String email);
}
*/

public interface AdminDao extends MongoRepository<Admin,String> {

    public Admin findByEmail(String email);

}