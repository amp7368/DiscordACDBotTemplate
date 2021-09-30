package apple.template.template.discord;

import apple.discord.acd.ACD;
import apple.discord.acd.command.ACDCommandResponse;
import apple.discord.acd.command.CommandLogger;
import apple.discord.acd.command.CommandLoggerLevel;
import apple.discord.acd.command.DefaultCommandLoggerLevel;
import apple.template.template.TemplateConfig;
import apple.template.template.TemplateMain;
import apple.template.template.discord.help.HelpCommand;
import apple.template.template.discord.ping.PingCommand;
import apple.template.template.discord.slash.SlashCommandExample;
import apple.template.template.logging.LoggingNames;
import apple.template.template.permissions.TemplatePermissions;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;
import org.slf4j.event.Level;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class DiscordBot extends ListenerAdapter {
    public static final String PREFIX = TemplateConfig.getInstance().getPrefix();

    // for slash command updating
    private static final long APPLEBOTS_SERVER = 603039156892860417L;

    public static ACD ACD;
    public static JDA client;


    public DiscordBot() throws LoginException {
        TemplateMain.log("DiscordBot starting", Level.INFO, LoggingNames.PROGRAM);

        // todo refine what gateways are needed
        Collection<GatewayIntent> intents = new ArrayList<>(Arrays.asList(GatewayIntent.values()));
        intents.remove(GatewayIntent.GUILD_PRESENCES);
        intents.remove(GatewayIntent.GUILD_MEMBERS);

        JDABuilder builder = JDABuilder.create(intents);

        builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS);
        builder.setMemberCachePolicy(MemberCachePolicy.NONE);
        builder.setToken(TemplateConfig.getInstance().getToken());
        client = builder.build();
        client.getPresence().setPresence(Activity.playing(TemplateConfig.getInstance().getPrescence()), false);
        ACD = new ACD(PREFIX, client, APPLEBOTS_SERVER);
        TemplatePermissions.addAllPermissions(ACD);
        ACD.getCommandLogger().addLogger(new CloverLogger());

        // commands here
        new PingCommand(ACD);
        new HelpCommand(ACD);
        new SlashCommandExample(ACD);

        ACD.updateCommands();

        TemplateMain.log("DiscordBot started", Level.INFO, LoggingNames.PROGRAM);
    }

    private static class CloverLogger implements CommandLogger {
        @Override
        public void log(@NotNull MessageReceivedEvent event, ACDCommandResponse response) {
            // if something needs to be done on logged events, use this
        }

        @Override
        public boolean shouldLog(CommandLoggerLevel level) {
            return level.getLevel() != DefaultCommandLoggerLevel.IGNORE.getLevel();
        }
    }
}
