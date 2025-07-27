package com.bitfiddling.helidon.service;

import io.avaje.jsonb.Json;
import io.soabase.recordbuilder.core.RecordBuilder;

@Json
@RecordBuilder
@RecordBuilder.Options(publicBuilderConstructors = true)
public record Message(String message, String greeting) {}
