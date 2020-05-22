package com.example.si_broker.services;

import com.example.si_broker.api.v1.models.UserDTO;
import com.example.si_broker.domain.Log;

import java.util.List;

public interface LogService {
    List<Log> getAllLogs();
    Log addLog(Log log);
}

