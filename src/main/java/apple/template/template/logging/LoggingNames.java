package apple.template.template.logging;

import apple.utilities.logging.AppleLoggerName;

public enum LoggingNames implements AppleLoggerName {
    ALL("all"),
    PROGRAM("clover"),
    FAILURE("failure");

    private final String name;

    LoggingNames(String name) {
        this.name = name;
    }

    @Override
    public String getLoggerName() {
        return name;
    }
}
