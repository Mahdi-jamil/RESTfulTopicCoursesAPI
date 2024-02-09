package com.devesta.curricula.mappers;

public interface Mapper <A,B>{
    B mapTo(A a);
    A mapFrom (B b);

}
