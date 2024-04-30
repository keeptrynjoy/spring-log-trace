package spring.log.trace.core;

import java.util.UUID;

public record TraceId(String traceId, int level){

    public TraceId() {
        this(createId(), 0);
    }

    private static String createId(){
        return UUID.randomUUID().toString();
    }

    public TraceId createNextId(){
        return new TraceId(traceId,level + 1);
    }

    public TraceId createPreviousStep(){
        return new TraceId(traceId,level - 1);
    }

    public boolean isFirstLevel(){
        return level == 0;
    }
}
