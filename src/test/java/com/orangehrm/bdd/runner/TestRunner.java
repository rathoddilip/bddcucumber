package com.orangehrm.bdd.runner;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Entry point for {@code mvn test}. Runs every feature under src/test/resources/features.
 * Glue, tags and report plugins are configured in src/test/resources/junit-platform.properties.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class TestRunner {
}
