package com.uom.idecide.dao;

import com.uom.idecide.pojo.Researcher;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
/*
public interface ResearcherDao extends JpaRepository<Researcher,String>,JpaSpecificationExecutor<Researcher>{
    public Researcher findByEmail(String email);
}
*/


public interface ResearcherDao extends MongoRepository<Researcher,String> {

    public Researcher findByEmail(String email);

}