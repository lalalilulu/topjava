package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DurationCounterRule implements TestRule {
    private static final Logger log = LoggerFactory.getLogger(DurationCounterRule.class);
    public static final List<String> listDurationDescriptions = new ArrayList<>();

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long startTime = System.currentTimeMillis();
                try {
                    base.evaluate();
                } finally {
                    long endTime = System.currentTimeMillis();
                    log.info("Test duration: " + (endTime - startTime) + "ms");
                    listDurationDescriptions.add("Test " + description.getMethodName() + " took " + (endTime - startTime) + "ms");
                }
            }
        };
    }
}
