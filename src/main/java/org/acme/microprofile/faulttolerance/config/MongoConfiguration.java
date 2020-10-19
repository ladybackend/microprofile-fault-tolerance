package org.acme.microprofile.faulttolerance.config;


import io.quarkus.arc.config.ConfigProperties;



@ConfigProperties(prefix = "database")
public class MongoConfiguration   {

    public boolean up;
    public boolean isUp() {
        return up;
    }
    public void setUp(boolean up) {
        this.up = up;
    }
}
