package com.etour.etour_api.function;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(T t, U u, V v);
}
