package com.revamp.springdal.transform;

public interface TransformerInterface<E, D> {

    public void convertFromEntityToDTO(E e, D d);

    public void convertFromDTOToEntity(D d, E e);
}
