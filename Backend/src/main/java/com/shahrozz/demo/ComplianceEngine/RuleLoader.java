package com.shahrozz.demo.ComplianceEngine;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.util.List;

@Service
public class RuleLoader {

    private static final String RULES_PATH = "compliance-rules/";

    public List<ComplianceRule> loadRules(String framework) {
        try {
            ClassPathResource resource = new ClassPathResource(RULES_PATH + framework.toLowerCase() + ".yaml");

            // Create LoaderOptions and configure
            LoaderOptions loaderOptions = new LoaderOptions();
            Constructor constructor = new Constructor(ComplianceRule.class, loaderOptions);

            // Create TypeDescription for list handling
            TypeDescription typeDescription = new TypeDescription(ComplianceRule.class);
            constructor.addTypeDescription(typeDescription);

            Yaml yaml = new Yaml(constructor);

            return yaml.load(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load compliance rules", e);
        }
    }
}
