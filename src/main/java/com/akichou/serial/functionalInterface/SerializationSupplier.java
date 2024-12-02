package com.akichou.serial.functionalInterface;

import java.io.IOException;

@FunctionalInterface
public interface SerializationSupplier<T> {

    T doSerialize() throws IOException ;
}
