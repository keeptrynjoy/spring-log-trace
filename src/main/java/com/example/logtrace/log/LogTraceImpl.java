package com.example.logtrace.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class LogTraceImpl implements LogTrace{
    private static final String START_PREFIX = "--> ";
    private static final String COMPLETE_PREFIX = "<-- ";
    private static final String EX_PREFIX = "<X- ";

    private ThreadLocal<TraceId> traceHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceValuable();
        TraceId traceId = traceHolder.get();
        final Long startTimeMs = System.currentTimeMillis();

        log.info("[{}] {}{}",
                traceId.traceId(),
                addSpace(START_PREFIX, traceId.level()),
                message
        );

        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceValuable(){
        TraceId traceId = traceHolder.get();
        if(Objects.isNull(traceId)){
            traceHolder.set(new TraceId());
        } else {
            traceHolder.set(traceId.createNextId());
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Throwable e) {
        complete(status,e);
    }

    private void complete(TraceStatus status, Throwable e){
        final long stopTimeMs = System.currentTimeMillis();
        final long resultTimeMs = stopTimeMs - status.startTimeMs();
        TraceId traceId = status.traceId();

        if(Objects.isNull(e)) {
            log.info("[{}] {}{} time={}ms",
                    traceId.traceId(),
                    addSpace(COMPLETE_PREFIX, traceId.level()),
                    status.message(),
                    resultTimeMs
                    );
        } else {
            log.info("[{}] {}{} time={}ms ex={}",
                    traceId.traceId(),
                    addSpace(EX_PREFIX, traceId.level()),
                    status.message(),
                    resultTimeMs,
                    e.toString()
            );
        }
        releaseTraceValuable();
    }

    private void releaseTraceValuable(){
        TraceId traceId = traceHolder.get();
        if(traceId.isFirstLevel()){
            traceHolder.remove();
        } else {
            traceHolder.set(traceId.createPreviousStep());
        }
    }

    private static String addSpace(String prefix, int level){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append(i == level - 1 ? "|" + prefix : "|  ");
        }
        return sb.toString();
    }
}
