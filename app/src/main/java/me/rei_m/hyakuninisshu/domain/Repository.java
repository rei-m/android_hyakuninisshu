package me.rei_m.hyakuninisshu.domain;

import rx.Observable;

public interface Repository<T extends Entity<T, I>, I extends EntityIdentifier> {

    Observable<T> resolve(I identifier);

    Observable<T> asEntitiesList();

    Observable<Boolean> contains(I identifier);

    Observable<Boolean> contains(T entity);

    Observable<Void> store(T entity);

    Observable<Void> delete(EntityIdentifier identifier);

    Observable<Void> delete(T entity);
}
