package com.example.si_broker.state;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public interface State {

    void execute(Context context);
}
