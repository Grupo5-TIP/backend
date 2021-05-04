package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.StateNotFoundException;
import com.unq.edu.tpi.tip.backend.models.State;
import com.unq.edu.tpi.tip.backend.repositories.StateRepository;
import org.springframework.stereotype.Service;

@Service
public class StateService {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public void save(State state) {
        stateRepository.save(state);
    }

    public State findById(Long id) throws StateNotFoundException {
        return stateRepository.findById(id)
                .orElseThrow(() -> new StateNotFoundException());
    }
}
