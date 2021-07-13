package dev.pca.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class Engine {
    private static final Logger LOG = LogManager.getLogger(Engine.class.getName());

    public void startDefaultConfigs() {
        System.out.println("Hello PCA");
    }
}
