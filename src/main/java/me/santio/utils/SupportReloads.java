package me.santio.utils;

import java.lang.annotation.*;

/**
 * When given to a method with a EventHandler, the event
 * will run on enable with the plugin
 *
 * @apiNote This annotation only works for PlayerJoinEvent and PlayerQuitEvent
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SupportReloads {}
