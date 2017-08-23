package com.example.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationConverter;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class SampleProperty {
    private static Configuration configuration = null;

    static {
        try {
            Parameters params = new Parameters();
            ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                    new ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration>(PropertiesConfiguration.class)
                            .configure(params.fileBased()
                                    .setFile(new File("external.properties")));
            PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(),
                    null, 1, TimeUnit.SECONDS);
            trigger.start();

            builder.addEventListener(ConfigurationBuilderEvent.ANY, new EventListener<ConfigurationBuilderEvent>() {

                public void onEvent(ConfigurationBuilderEvent event) {
                    System.out.println("Event:" + event);
                    try {
                        if (event.getEventType() == ConfigurationBuilderEvent.RESET) {
                            configuration = builder.getConfiguration().subset("prefix");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            configuration = builder.getConfiguration().subset("prefix");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStringProp1() {
        return configuration.getString("stringProp1");
    }

    public Map getMapProp() {
        return ConfigurationConverter.getMap(configuration.subset("mapProp"));
    }
}