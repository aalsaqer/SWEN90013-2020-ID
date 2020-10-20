package com.uom.idecide.service;

import com.uom.idecide.dao.AnswerDao;
import com.uom.idecide.dao.KVDao;
import com.uom.idecide.pojo.Answer;
import com.uom.idecide.pojo.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KVService {

    @Autowired
    private KVDao kVDao;

    public void add(KeyValue kv) {
        kVDao.save(kv);
    }

    public String findById(String id) {
        return kVDao.findById(id).get().getValue();
    }

    public void deleteById(String id) {
        kVDao.deleteById(id);
    }
}
