package org.diegochinchilla.models;

import java.util.Map;

public class ConnectorConfig {
    private String name;
    private Map<String, Object> config;

    // Constructor por defecto (necesario para Jackson)
    public ConnectorConfig() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "ConnectorConfig{" +
               "name='" + name + '\'' +
               ", config=" + config +
               '}';
    }
}