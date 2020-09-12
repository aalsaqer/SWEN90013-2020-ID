package com.uom.idecide.dao;

import com.uom.idecide.pojo.ActionPlan;
import com.uom.idecide.pojo.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionPlanDao extends MongoRepository<ActionPlan,String> {
    public void deleteActionPlanByStrategyName(String strategyName);
    public ActionPlan findByStrategyName(String strategyName);
}
