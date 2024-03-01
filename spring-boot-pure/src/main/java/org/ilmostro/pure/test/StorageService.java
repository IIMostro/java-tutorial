package org.ilmostro.pure.test;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class StorageService {

    @PostConstruct
    public void initialize(){
        StorageServiceFactory.register("local", this);
    }

    @MethodAdapter
    public void save(String fileName, String context){
        // todo save file
    }
}
