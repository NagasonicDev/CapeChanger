package au.nagasonic.capechanger;

import au.nagasonic.capechanger.commands.CapeCommands;
import au.nagasonic.capechanger.util.UpdateChecker;
import au.nagasonic.capechanger.util.Util;
import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.profile.PlayerTextures;

import java.net.URL;
import java.util.logging.Logger;

public final class CapeChanger extends JavaPlugin {
    private static CapeChanger instance;
    private static Logger logger;
    public static String path;


    @Override
    public void onEnable() {
        // Plugin startup logic
        long start = System.currentTimeMillis();
        instance = this;
        logger = getLogger();
        path = getDataFolder().getPath();
        this.getCommand("cape").setExecutor(new CapeCommands());
        this.getCommand("cape").setTabCompleter(new CapeCommands());
        String version = getDescription().getVersion();
        if (version.contains("dev")) {
            Util.log("&eThis is a DEV build, things may not work as expected, please report any bugs on GitHub");
            Util.log("&ehttps://github.com/NagasonicDev/CapeChanger/issues");
        }
        new UpdateChecker(this);
        Metrics metrics = new Metrics(this, 24153);
        Util.log("&aSuccessfully enabled v%s&7 in &b%.2f seconds", version, (float) (System.currentTimeMillis() - start) / 1000);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static boolean setCape(Player player, Cape cape) {
        PlayerProfile profile = player.getPlayerProfile();
        URL skin = profile.getTextures().getSkin();
        PlayerTextures textures = profile.getTextures();
        if (cape.url != textures.getCape()){
            textures.setCape(cape.url);
            textures.setSkin(skin);
            profile.setTextures(textures);
            player.setPlayerProfile(profile);
            return true;
        }
        return false;

    }

    public static Cape getCape(Player player){
        PlayerProfile profile = player.getPlayerProfile();
        URL capeURL = profile.getTextures().getCape();
        Cape cape = new Cape(capeURL.toString());
        return cape;
    }


}
