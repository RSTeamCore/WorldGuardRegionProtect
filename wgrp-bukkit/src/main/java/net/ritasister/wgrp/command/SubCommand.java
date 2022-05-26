package net.ritasister.wgrp.command;

import net.ritasister.wgrp.util.config.Message;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommand {
    String name();

    @NotNull String permission() default "";

    String[] aliases() default {};

    Message description();
}
