package net.ritasister.wgrp.util.utility;

import net.ritasister.wgrp.command.extend.CommandWGRP;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class CheckResult {

    private final boolean success;
    private final String errorMessage;
    private final CommandWGRP.Check check;

    private CheckResult(boolean success, String errorMessage, CommandWGRP.Check check) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.check = check;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull CheckResult success(CommandWGRP.Check check) {
        return new CheckResult(true, null, check);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull CheckResult error(String errorMessage) {
        return new CheckResult(false, errorMessage, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public CommandWGRP.Check getCheck() {
        return check;
    }
}
