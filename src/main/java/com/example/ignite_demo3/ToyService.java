package com.example.ignite_demo3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToyService {
    private final Ignite IgniteInstance;
    private final ToyRepository toyRepository;

    public List<ToyModel> getToy(){
        List<ToyModel> toyList = new ArrayList<>();
        Iterable<ToyModel> toys = toyRepository.findAll();
        //toys.forEach(toyList::add);
        while (toys.iterator().hasNext()){
            //log.info("toy: {}", toys.iterator().next());
            toyList.add(toys.iterator().next());
            //toyList.add(toys.iterator().next());
        }

        log.info("toyList: {}", toyList);



        return toyList;
    }
    public List<ToyModel> getIdToy(String id){
        List<ToyModel> toyList = new ArrayList<>();
        Optional<ToyModel> toys = toyRepository.findById(id);
        //toys.forEach(toyList::add);
        toys.ifPresent(toyList::add);

        log.info("toyList: {}", toyList);



        return toyList;
    }
    public List<ToyModel> insertToy(ToyModel toyModel){
        Map<String, ToyModel> addToy = new TreeMap<>();
        addToy.put(toyModel.getToy_id(), toyModel);

        List<ToyModel> toyList = new ArrayList<>();
        Iterable<ToyModel> result = toyRepository.save(addToy);

        for(ToyModel  toy: result){
            toyList.add(toy);
        }
        return toyList;
    }
}
