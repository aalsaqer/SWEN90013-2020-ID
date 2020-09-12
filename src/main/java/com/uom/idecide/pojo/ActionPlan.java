package com.uom.idecide.pojo;

public class ActionPlan {
    private String strategyName;
    private String declarative;
    private String strategy;

    public ActionPlan() {}

    public ActionPlan(String strategyName, String declarative, String strategy) {
        this.strategyName = strategyName;
        this.declarative = declarative;
        this.strategy = strategy;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getDeclarative() {
        return declarative;
    }

    public void setDeclarative(String declarative) {
        this.declarative = declarative;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
}
