package com.example.logtrace.log;

public record TraceStatus (TraceId traceId, Long startTimeMs, String message){
}
