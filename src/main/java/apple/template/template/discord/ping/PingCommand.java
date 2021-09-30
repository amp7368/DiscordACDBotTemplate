package apple.template.template.discord.ping;

import apple.discord.acd.ACD;
import apple.discord.acd.command.ACDCommand;
import apple.discord.acd.command.DiscordCommandAlias;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends ACDCommand {
    public PingCommand(ACD acd) {
        super(acd);
    }

    @DiscordCommandAlias(alias = "ping", overlappingCommands = "ping", order = 2)
    public void ping(MessageReceivedEvent event) {
        event.getChannel().sendMessage("pong").queue();
    }

    @DiscordCommandAlias(alias = "ping ping", overlappingCommands = "ping", order = 1)
    public void pingping(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Whoa whoa whoa! Now you're getting a little crazy").queue();
    }
}
