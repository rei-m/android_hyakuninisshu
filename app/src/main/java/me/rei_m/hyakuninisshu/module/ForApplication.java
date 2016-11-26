package me.rei_m.hyakuninisshu.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Custom annotation for getting application context
 * Example:
 * <code>public void int provideSomething(@ForApplication Context context)...</code>
 * or
 * <code>public Myconstructor(@ForApplication Context context)</code>
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ForApplication {
}
