package apple.template.template;

import apple.template.template.io.FileIOService;
import apple.utilities.database.SaveFileable;
import apple.utilities.database.singleton.AppleJsonDatabaseSingleton;
import apple.utilities.util.FileFormatting;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TemplateConfig implements SaveFileable {
    private static TemplateConfig instance;
    private static final File rootFolder = FileFormatting.getDBFolder(TemplateMain.class);

    private boolean isLogsVersionDate = true;
    private boolean isLogsShouldAppend = true;
    private String token = "token_here";
    private long[] errorLoggers = new long[0];
    private String prefix = "ex!";
    private String status = "ex!help";

    public TemplateConfig() {
    }

    protected static TemplateConfig load() throws IOException {
        File file = FileFormatting.fileWithChildren(rootFolder, "config");
        AppleJsonDatabaseSingleton<TemplateConfig> manager = new AppleJsonDatabaseSingleton<TemplateConfig>(file, FileIOService.get());
        instance = manager.loadNow(TemplateConfig.class, getSaveFileNameStatic());
        if (instance == null) instance = new TemplateConfig();
        manager.save(instance);
        SimpleDateFormat dateFormat = new SimpleDateFormat("--yy.MM.dd--hh'h'mm'm'");
        String dateString = instance.isLogsVersionDate() ? dateFormat.format(new Date()) : "";
        System.setProperty("logFile.version", dateString);
        System.setProperty("logFile.shouldAppend", String.valueOf(instance.isLogsShouldAppend()));
        System.setProperty("logFile.homeDir", instance.getRootFolder().getAbsolutePath());
        return instance;
    }

    public File getRootFolder() {
        return rootFolder;
    }

    public boolean isLogsVersionDate() {
        return isLogsVersionDate;
    }

    public boolean isLogsShouldAppend() {
        return isLogsShouldAppend;
    }

    public String getToken() {
        return token;
    }

    public static TemplateConfig getInstance() {
        return instance;
    }

    public long[] getErrorLoggers() {
        return errorLoggers;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPrescence() {
        return status;
    }

    @Override
    public String getSaveFileName() {
        return getSaveFileNameStatic();
    }

    public static String getSaveFileNameStatic() {
        return "config.properties";
    }
}
