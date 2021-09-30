package apple.template.template.logging;

import apple.discord.acd.MillisTimeUnits;
import apple.template.template.TemplateConfig;
import apple.template.template.TemplateMain;
import apple.template.template.discord.DiscordBot;
import apple.utilities.logging.AppleLoggerName;
import apple.utilities.logging.AppleTimedBufferedLogger;
import apple.utilities.logging.LogMessage;
import apple.utilities.util.ExceptionUnpackaging;
import apple.utilities.util.FileFormatting;
import apple.utilities.util.Pretty;
import org.slf4j.event.Level;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ErrorLogger extends AppleTimedBufferedLogger {
    public ErrorLogger() {
        super(MillisTimeUnits.HOUR);
    }

    @Override
    public void logInternal(LogMessage logMessage) {
        super.logInternal(logMessage);
    }

    @Override
    public String getLoggerName() {
        return "failure";
    }

    @Override
    public Level[] getWatchLevels() {
        return new Level[]{Level.ERROR};
    }

    @Override
    protected void flush(List<LogMessage> messages) {
        File file = FileFormatting.fileWithChildren(FileFormatting.getDBFolder(TemplateMain.class), "logs", "sendLogs", "errors.log");
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (LogMessage message : messages) {
                writer.write(String.format("[%s] [%s] - %s",
                        message.level().toString(),
                        Pretty.join(", ", AppleLoggerName::getLoggerName, message.loggedNames()),
                        message.message())
                );
            }
        } catch (IOException e) {
            TemplateMain.log(ExceptionUnpackaging.getStackTrace(e), Level.ERROR, LoggingNames.PROGRAM);
        }
        long[] loggers = TemplateConfig.getInstance().getErrorLoggers();
        for (long logger : loggers) {
            DiscordBot.client.retrieveUserById(logger).queue(user -> {
                user.openPrivateChannel().queue(
                        channel -> {
                            channel.sendFile(file).queue();
                        }
                );
            });
        }
    }
}
