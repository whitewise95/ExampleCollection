package com.example.examplecollection.utils.interfaces;

import org.mapstruct.MappingTarget;

public interface ActDtoMapper<E, R, C, U> {

	R toResponse(E entity);

	E create(C dto);

	void update(U dto, @MappingTarget E entity);
}
