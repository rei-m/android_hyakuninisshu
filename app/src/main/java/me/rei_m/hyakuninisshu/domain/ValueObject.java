package me.rei_m.hyakuninisshu.domain;

/**
 * Interface of ValueObject.
 */
public interface ValueObject {

    /**
     * compare equivalence of object.
     *
     * @param that target object.
     * @return if equal{@code true}
     */
    boolean equals(Object that);

    /**
     * return hashcode of object.
     *
     * @return hashcode.
     */
    int hashCode();
}
