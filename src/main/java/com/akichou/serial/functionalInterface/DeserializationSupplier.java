package com.akichou.serial.functionalInterface;

import java.io.IOException;

@FunctionalInterface
public interface DeserializationSupplier<T> {

    T doDeserialize() throws IOException, ClassNotFoundException ;
}
