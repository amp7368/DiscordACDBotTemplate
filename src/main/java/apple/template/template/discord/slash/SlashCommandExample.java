package apple.template.template.discord.slash;

import apple.discord.acd.ACD;
import apple.discord.acd.slash.ACDSlashCommandHandler;
import apple.discord.acd.slash.base.ACDBaseCommand;
import apple.discord.acd.slash.base.ACDSlashCommand;
import apple.discord.acd.slash.options.SlashOptionDefault;
import apple.discord.acd.slash.options.SlashOptionType;
import apple.discord.acd.slash.runner.ACDSlashMethodCommand;
import apple.discord.acd.slash.sub.ACDSlashSubCommand;
import apple.discord.acd.slash.sub.ACDSubCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@ACDBaseCommand(alias = "base", description = "an example command")
public class SlashCommandExample extends ACDSlashCommand {
    public SlashCommandExample(ACD acd) {
        super(acd);
    }

    @ACDSubCommand(alias = "sub-command", description = "sub-command description")
    public class SlashSubCommand extends ACDSlashSubCommand {
        public SlashSubCommand(String path, ACD acd, ACDSlashCommandHandler parent) {
            super(path, acd, parent);
        }

        @ACDSlashMethodCommand
        public MessageEmbed run(SlashCommandEvent event,
                                @SlashOptionDefault(
                                        optionType = OptionType.MENTIONABLE, name = "mention-someone", description = "please", slashOptionType = SlashOptionType.MEMBER
                                ) Member mention,
                                @SlashOptionDefault(
                                        optionType = OptionType.INTEGER, name = "number", description = "number description"
                                ) long integer,
                                @SlashOptionDefault(
                                        optionType = OptionType.STRING, name = "words", description = "words description",
                                        choicesNames = {"mageName", "warriorName", "archerName"},
                                        choicesValues = {"mage", "warrior", "archer"}
                                ) String words
        ) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("You are a " + words);
            embedBuilder.addField("Number", "value you entered was #" + integer, false);
            if (mention != null)
                embedBuilder.setAuthor(mention.getEffectiveName(), null, mention.getGuild().getIconUrl());
            return embedBuilder.build();
        }
    }
}
