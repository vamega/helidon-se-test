package com.bitfiddling.helidon.service;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
@RecordBuilder.Options(publicBuilderConstructors = true)
public record Message(String message, String greeting) {}
