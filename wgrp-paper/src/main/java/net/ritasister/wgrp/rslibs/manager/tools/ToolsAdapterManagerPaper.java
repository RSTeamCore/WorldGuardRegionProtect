package net.ritasister.wgrp.rslibs.manager.tools;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.entity.Player;
import net.ritasister.wgrp.api.manager.tools.ToolsAdapterManager;

public class ToolsAdapterManagerPaper implements ToolsAdapterManager<Player> {

    public boolean isSuperPickaxeActive(Player player) {
        if (player == null) {
            return false;
        }

        final LocalSession session = WorldEdit.getInstance().getSessionManager().getIfPresent(player);

        if (session != null && session.hasSuperPickAxe()) {
            session.disableSuperPickAxe();
            return true;
        }

        return false;
    }
    
}
