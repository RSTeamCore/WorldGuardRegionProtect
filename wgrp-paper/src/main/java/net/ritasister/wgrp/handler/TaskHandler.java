package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.util.schedulers.FoliaRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class TaskHandler implements Handler<Void> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public TaskHandler(WorldGuardRegionProtectPaperPlugin wgrpPaperPlugin) {
        this.wgrpPlugin = wgrpPaperPlugin;
    }

    public void handle() {

        final List<FoliaRunnable> allTasks = List.of();

        allTasks.forEach(task -> {
            this.wgrpPlugin.getLogger().info("Registered task: " + task.getClass().getSimpleName());
            wgrpPlugin.getTaskMap().put(task.getClass(), task);
        });
        this.wgrpPlugin.getLogger().info(String.format("All tasks registered successfully! List of available registered tasks: %s",
                allTasks.stream()
                        .map(task -> task.getClass().getSimpleName())
                        .collect(Collectors.joining(", "))));

    }
}
