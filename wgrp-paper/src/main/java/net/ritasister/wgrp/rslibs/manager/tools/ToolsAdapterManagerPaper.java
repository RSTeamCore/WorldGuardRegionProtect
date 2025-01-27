package net.ritasister.wgrp.rslibs.manager.tools;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.entity.Player;
import net.ritasister.wgrp.api.manager.tools.ToolsAdapterManager;

import java.util.Optional;

public class ToolsAdapterManagerPaper implements ToolsAdapterManager<Player> {

    public boolean isSuperPickaxeActive(Player player) {
        return Optional.ofNullable(WorldEdit.getInstance().getSessionManager().getIfPresent(player))
                .filter(LocalSession::hasSuperPickAxe)
                .map(session -> {
                    session.disableSuperPickAxe();
                    return true;
                }).orElse(false);
    }
    
}
