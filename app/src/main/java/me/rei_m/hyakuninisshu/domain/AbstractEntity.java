package me.rei_m.hyakuninisshu.domain;

import android.support.annotation.NonNull;

public abstract class AbstractEntity<T extends Entity<T, I>, I extends EntityIdentifier> implements Entity<T, I> {

    private final I identifier;

    protected AbstractEntity(@NonNull I identifier) {
        this.identifier = identifier;
    }

    @Override
    public I identifier() {
        return identifier;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error("clone not supported");
        }
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        return this == that ||
                !(that == null || !(that instanceof Entity)) && identifier.equals(((Entity) that).identifier());
    }
}
