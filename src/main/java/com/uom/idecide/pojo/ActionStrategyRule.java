package com.uom.idecide.pojo;

import org.springframework.data.annotation.Id;

public class ActionStrategyRule {
    @Id
    private Integer id = 1;
    private String jsonStr;

    public ActionStrategyRule(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public ActionStrategyRule() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }


}
