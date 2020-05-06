package com.example.si_broker.state;

import com.example.si_broker.domain.ServiceDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestScope
public class Context {
    private String complexServiceParameters;
    private ServiceDomain serviceDomain;

    private State state;


    public Context() {
    }


    public String getComplexServiceParameters() {
        return complexServiceParameters;
    }

    public void setComplexServiceParameters(String complexServiceParameters) {
        this.complexServiceParameters = complexServiceParameters;
    }

    public ServiceDomain getServiceDomain() {
        return serviceDomain;
    }

    public void setServiceDomain(ServiceDomain serviceDomain) {
        this.serviceDomain = serviceDomain;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
