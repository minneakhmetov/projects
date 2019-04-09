package com.spinach.services;

import com.spinach.dao.IgnoreRepository;
import com.spinach.models.IgnoreModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IgnoreService {

    @Autowired
    private IgnoreRepository repository;

    public void saveOneUser(long userId, long partnerId){
        repository.addOneUser(userId, partnerId);
    }

    public void saveToBothUsers(long userId, long partnerId){
        List<IgnoreModel> list = new ArrayList<IgnoreModel>();
        list.add(IgnoreModel.builder().userId(userId).partnerId(partnerId).build());
        list.add(IgnoreModel.builder().userId(partnerId).partnerId(userId).build());
        repository.saveAll(list);
    }

    public boolean exists(long userId, long partnerId){
        return repository.read(userId, partnerId) != null;
    }

    public List<IgnoreModel> read(long userId){
        return repository.read(userId);
    }
}
