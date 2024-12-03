package com.notifyme.services;

import com.notifyme.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnidadeService {

    @Autowired
    private UnidadeRepository repository;

}
