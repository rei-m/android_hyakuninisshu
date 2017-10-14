package me.rei_m.hyakuninisshu.domain;

public interface Entity<T extends Entity<T, I>, I extends EntityIdentifier> extends Cloneable {

    /**
     * get identifier of entity。
     *
     * @return {@link EntityIdentifier}
     */
    I identifier();

    /**
     * compare equivalence of object. use identifier{@link #identifier()}
     *
     * @param that target object
     * @return if target has same identifier{@code true}
     */
    boolean equals(Object that);

    /**
     * return hashcode of object.
     *
     * @return hashcode
     */
    int hashCode();

    /**
     * create clone of entity.
     *
     * @return entity
     */
    T clone();
}
