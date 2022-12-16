package net.ritasister.wgrp.command;

import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommand {
    String name();

    @NotNull UtilPermissions permission() default UtilPermissions.NULL_PERM;

    String[] aliases() default {};

    String[] tabArgs() default {};

    String description();
}
