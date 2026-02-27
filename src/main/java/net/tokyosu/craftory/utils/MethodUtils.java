package net.tokyosu.craftory.utils;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class MethodUtils {
    public static void printMethod(@NotNull String className, Logger logger) {
        try {
            Class<?> clazz = Class.forName(className);
            logger.info("✓ Target class found: {}", clazz.getName());

            // List all methods
            for (java.lang.reflect.Method method : clazz.getDeclaredMethods()) {
                logger.info("  Method: {} - {}", method.getName(), method);
            }
        } catch (ClassNotFoundException e) {
            logger.error("✗ Target class NOT found!");
        }
    }
}
