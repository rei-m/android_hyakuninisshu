package me.rei_m.hyakuninisshu.di;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom annotation for getting application context
 * Example:
 * <code>public void int provideSomething(@ForApplication Context context)...</code>
 * or
 * <code>public Myconstructor(@ForApplication Context context)</code>
 */
@Qualifier
@Retention(RUNTIME)
public @interface ForApplication {
}
