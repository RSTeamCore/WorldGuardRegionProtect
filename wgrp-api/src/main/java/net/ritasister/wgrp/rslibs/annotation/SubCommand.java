package net.ritasister.wgrp.rslibs.annotation;

import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for create subcommands.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommand {

    /**
     * Set a subcommand name.
     * @return subcommand name.
     */
    String name();

    /**
     * Set a permissions for subcommand.
     * @return permission for subcommand.
     */
    @NotNull UtilPermissions permission() default UtilPermissions.NULL_PERM;

    /**
     * Create aliases for subcommand.
     * @return aliases of subcommand.
     */
    String[] aliases() default {};

    /**
     * Args
     * @return tab complete arguments of subcommand.
     */
    String[] tabArgs() default {};

    /**
     * Set description of subcommand.
     * @return description.
     */
    String description();
}
