package com.example.si_broker.services.impl;

import com.example.si_broker.api.v1.models.ServiceDTO;
import com.example.si_broker.domain.Log;
import com.example.si_broker.domain.ServiceDomain;
import com.example.si_broker.repositories.LogRepository;
import com.example.si_broker.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository){
        this.logRepository = logRepository;
    }

    @Override
    public List<Log> getAllLogs() {
        return new ArrayList<>(logRepository.findAll());
    }

    @Override
    public Log addLog(Log log) {
        return logRepository.save(log);
    }


}
