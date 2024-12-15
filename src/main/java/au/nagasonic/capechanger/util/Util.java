package au.nagasonic.capechanger.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final String PREFIX = "&7[&eCapeChanger&7] ";
    private static final String PREFIX_ERROR = "&7[&eCapeChanger &cERROR&7] ";
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f\\d]){6}>");

    @SuppressWarnings("deprecation") // Paper deprecation
    public static String getColString(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        while (matcher.find()) {
            final ChatColor hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
            final String before = string.substring(0, matcher.start());
            final String after = string.substring(matcher.end());
            string = before + hexColor + after;
            matcher = HEX_PATTERN.matcher(string);
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendColMsg(CommandSender receiver, String format, Object... objects) {
        receiver.sendMessage(getColString(String.format(format, objects)));
    }

    public static void log(String format, Object... objects) {
        String log = String.format(format, objects);
        Bukkit.getConsoleSender().sendMessage(getColString(PREFIX + log));
    }


    public static void debug(String format, Object... objects) {
        String debug = String.format(format, objects);
        Bukkit.getConsoleSender().sendMessage(getColString(PREFIX_ERROR + debug));
    }

    private static final List<String> DEBUGS = new ArrayList<>();

    public static void logLoading(String format, Object... objects) {
        String form = String.format(format, objects);
        DEBUGS.add(form);
        log(form);
    }

    public static List<String> getDebugs() {
        return DEBUGS;
    }

    /**
     * Convert a UUID to an int array
     * <p>Used for Minecraft 1.16+</p>
     *
     * @param uuid UUID to convert
     * @return int array from UUID
     */
    public static int[] uuidToIntArray(UUID uuid) {
        long most = uuid.getMostSignificantBits();
        long least = uuid.getLeastSignificantBits();
        return new int[]{(int) (most >> 32), (int) most, (int) (least >> 32), (int) least};
    }

    public static Date getDate(){
        Date date = new Date();
        return date;
    }

    public static String fromDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        return format.format(date);
    }

    public static boolean isURL(String url){
        boolean c = false;
        try {
            URL Url = new URL(url);
            c = true;
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        return c;
    }
    public static int parseInt(final String s) {
        assert s.matches("-?\\d+");
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            return s.startsWith("-") ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}
