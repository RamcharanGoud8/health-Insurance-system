package com.hi.eligibilitydetermination.factory;

import com.hi.eligibilitydetermination.strategy.PlanStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PlanFactory {
    private final Map<String, PlanStrategy> strategyMap;

    public PlanFactory(Map<String, PlanStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public PlanStrategy getPlan(String planName) {

        PlanStrategy strategy = strategyMap.get(planName.toUpperCase());

        if(strategy == null) {
            throw new RuntimeException("Invalid Plan");
        }

        return strategy;
    }
}
