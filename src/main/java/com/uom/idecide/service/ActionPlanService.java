package com.uom.idecide.service;

import com.uom.idecide.dao.ActionPlanDao;
import com.uom.idecide.dao.ActionStrategyRuleDao;
import com.uom.idecide.pojo.ActionPlan;
import com.uom.idecide.pojo.ActionStrategyRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ActionPlanService {

    @Autowired
    private ActionPlanDao actionPlanDao;
    @Autowired
    private ActionStrategyRuleDao actionStrategyRuleDao;


    public void add(ActionPlan actionPlan) {
        actionPlanDao.save(actionPlan);
    }

    public void delete(String strategyName){
        actionPlanDao.deleteActionPlanByStrategyName(strategyName);
    }

    public void update(ActionPlan actionPlan){
        delete(actionPlan.getStrategyName());
        add(actionPlan);
    }

    public ActionPlan findByStrategyName(String strategyName){
        return actionPlanDao.findByStrategyName(strategyName);
    }

    public List<ActionPlan> findAll(){
        return actionPlanDao.findAll();
    }

    public void saveActionStrategyRule(ActionStrategyRule actionStrategyRule){
        actionStrategyRuleDao.save(actionStrategyRule);
    }


    public String getActionStrategyRule(){
        ActionStrategyRule actionStrategyRule = actionStrategyRuleDao.findById(1).get();
        String jsonStr = actionStrategyRule.getJsonStr();
        return jsonStr;
    }
}
