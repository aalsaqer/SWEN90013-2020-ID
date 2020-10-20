package com.uom.idecide.dao;

import com.uom.idecide.pojo.KeyValue;
import com.uom.idecide.pojo.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface KVDao extends MongoRepository<KeyValue,String> {
}
