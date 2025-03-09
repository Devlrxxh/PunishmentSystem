package dev.lrxh.punishmentSystem.punishment.menu;

import dev.lrxh.punishmentSystem.configs.impl.SettingsLocale;
import dev.lrxh.punishmentSystem.profile.Profile;
import dev.lrxh.punishmentSystem.punishment.Punishment;
import dev.lrxh.punishmentSystem.utils.TimeUtil;
import dev.lrxh.punishmentSystem.utils.menu.Button;
import dev.lrxh.punishmentSystem.utils.menu.Filter;
import dev.lrxh.punishmentSystem.utils.menu.Menu;
import dev.lrxh.punishmentSystem.utils.menu.impl.DisplayButton;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PunishmentHistoryMenu extends Menu {
    private final Profile profile;

    public PunishmentHistoryMenu(Profile profile, String name) {
        super(SettingsLocale.HISTORY_MENU_TITLE.getString().replace("<player>", name), SettingsLocale.HISTORY_MENU_SIZE.getInt(), Filter.FILL);
        this.profile = profile;
    }

    @Override
    public List<Button> getButtons(Player player) {
        List<Button> buttons = new ArrayList<>();
        int i = 10;

        for (Punishment punishment : profile.getPunishments()) {
            List<String> lore = new ArrayList<>();

            for (String line : SettingsLocale.HISTORY_MENU_PUNISHMENT_LORE.getStringList()) {
                line = line.replaceAll("<issuer>", punishment.getIssuer());
                line = line.replaceAll("<issuedOn>", punishment.getIssuedOnString());
                line = line.replaceAll("<duration>", TimeUtil.unparse(punishment.getDuration()));

                lore.add(line);
            }

             buttons.add(new DisplayButton(i++, punishment.getIcon(), SettingsLocale.HISTORY_MENU_PUNISHMENT_NAME.getString().replace("<name>", punishment.getType().getName()), lore));
        }

        return buttons;
    }
}
