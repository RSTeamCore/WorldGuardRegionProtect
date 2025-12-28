package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.util.schedulers.FoliaRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class TaskHandler implements Handler<Void> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;
    private final List<FoliaRunnable> tasks;

    public TaskHandler(WorldGuardRegionProtectPaperPlugin plugin, List<FoliaRunnable> tasks) {
        this.wgrpPlugin = plugin;
        this.tasks = tasks;
    }

    @Override
    public void handle() {
        tasks.forEach(task -> {
            wgrpPlugin.getLogger().info("Registered task: " + task.getClass().getSimpleName());
            wgrpPlugin.getTaskMap().put(task.getClass(), task);
        });
        wgrpPlugin.getLogger().info("All tasks registered successfully! List: " +
                tasks.stream().map(t -> t.getClass().getSimpleName()).collect(Collectors.joining(", ")));
    }
}
