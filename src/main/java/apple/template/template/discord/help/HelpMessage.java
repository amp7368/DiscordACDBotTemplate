package apple.template.template.discord.help;

import apple.discord.acd.ACD;
import apple.discord.acd.MillisTimeUnits;
import apple.discord.acd.reaction.gui.ACDGuiPageable;
import apple.discord.acd.reaction.gui.GuiPageMessageable;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class HelpMessage extends ACDGuiPageable {
    public HelpMessage(ACD acd, MessageChannel channel) {
        super(acd, channel);
        for (int i = 0; i < 99; i++) {
            int finalI = i;
            new DynamicPage<HelpPageNumber>(() -> new HelpPageNumber(finalI));
        }
        addPage(this::lastPage);
    }

    private Message lastPage() {
        MessageBuilder messageBuilder = new MessageBuilder();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("yayay! Page 100!");
        messageBuilder.setEmbeds(embedBuilder.build());
        return messageBuilder.build();
    }

    @Override
    protected long getMillisToOld() {
        return MillisTimeUnits.MINUTE_15;
    }

    private static class HelpPageNumber implements GuiPageMessageable {
        private int i;

        public HelpPageNumber(int i) {
            this.i = i;
        }

        @Override
        public Message asMessage() {
            MessageBuilder messageBuilder = new MessageBuilder();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(String.format("Page %d/100",i));
            messageBuilder.setEmbeds(embedBuilder.build());
            return messageBuilder.build();
        }
    }
}
