package apple.template.template.discord.help;

import apple.discord.acd.ACD;
import apple.discord.acd.command.ACDCommand;
import apple.discord.acd.command.DiscordCommandAlias;
import apple.discord.acd.parameters.ParameterDefined;
import apple.template.template.permissions.TemplatePermissions;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand extends ACDCommand {
    public HelpCommand(ACD acd) {
        super(acd);
    }

    @DiscordCommandAlias(alias = "help",permission = TemplatePermissions.ADMIN,overlappingCommands = "help")
    public void ping(MessageReceivedEvent event, @ParameterDefined(usage = "string here") int hi) {
        new HelpMessage(acd, event.getChannel());
    }
    @DiscordCommandAlias(alias = "help",permission = TemplatePermissions.NOT_ADMIN,overlappingCommands = "help")
    public void pin1g(MessageReceivedEvent event, @ParameterDefined(usage = "string here") int hi) {
        new HelpMessage(acd, event.getChannel());
    }
}
