package spring.log.trace.core;

public record TraceStatus (TraceId traceId, Long startTimeMs, String message){
}
