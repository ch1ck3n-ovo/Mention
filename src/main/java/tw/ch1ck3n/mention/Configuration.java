package tw.ch1ck3n.mention;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    /** Messages */
    public final List<String> help;

    public final String title, subtitle, actionbar, reloading, reloaded;

    /** Settings */
    public final int mode, runAfter, titleFadeIn, titleStay, titleFadeOut;

    public Configuration() {
        /** Messages */
        List<String> temp = new ArrayList<>();
        for (String s: Mention.INSTANCE.getConfig().getStringList("messages.help")) temp.add(s.replaceAll("&", "§"));
        this.help = temp;

        this.title = Mention.INSTANCE.getConfig().getString("messages.title").replaceAll("&", "§");
        this.subtitle = Mention.INSTANCE.getConfig().getString("messages.subtitle").replaceAll("&", "§");
        this.actionbar = Mention.INSTANCE.getConfig().getString("messages.actionbar").replaceAll("&", "§");
        this.reloading = Mention.INSTANCE.getConfig().getString("messages.reloading").replaceAll("&", "§");
        this.reloaded = Mention.INSTANCE.getConfig().getString("messages.reloaded").replaceAll("&", "§");

        /** Settings */
        this.mode = Mention.INSTANCE.getConfig().getInt("settings.mode");
        this.runAfter = Mention.INSTANCE.getConfig().getInt("settings.run-after");
        this.titleFadeIn = Mention.INSTANCE.getConfig().getInt("settings.title-fade-in");
        this.titleStay = Mention.INSTANCE.getConfig().getInt("settings.title-stay");
        this.titleFadeOut = Mention.INSTANCE.getConfig().getInt("settings.title-fade-out");
    }
}
