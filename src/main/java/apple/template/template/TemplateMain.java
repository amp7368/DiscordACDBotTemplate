package apple.template.template;

import apple.template.template.discord.DiscordBot;
import apple.template.template.logging.ErrorLogger;
import apple.template.template.logging.LoggingNames;
import apple.utilities.logging.AppleLoggerManager;
import apple.utilities.logging.AppleLoggerName;
import apple.utilities.util.ArrayUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class TemplateMain {
    public static TemplateConfig CONFIG;
    private static AppleLoggerManager LOGGER;

    public static void main(String[] args) throws LoginException {
        try {
            CONFIG = TemplateConfig.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER = new AppleLoggerManager(
                ArrayUtils.combine(LoggingNames.values(), new ErrorLogger(), AppleLoggerName[]::new),
                LoggerFactory.getLogger("clover")
        );
        new DiscordBot();
    }

    public static void log(String msg, Level lvl, LoggingNames... loggerName) {
        LOGGER.log(msg, lvl, loggerName);
    }
}
