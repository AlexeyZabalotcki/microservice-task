package com.specific.group.currency.client.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specific.group.currency.client.cache.annotation.RedisReactiveCacheAdd;
import com.specific.group.currency.client.cache.annotation.RedisReactiveCacheEvict;
import com.specific.group.currency.client.cache.annotation.RedisReactiveCacheGet;
import com.specific.group.currency.client.cache.utils.AspectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
@ConditionalOnClass({ReactiveRedisTemplate.class})
@RequiredArgsConstructor
public class Cache {

    private final ReactiveRedisTemplate reactiveRedisTemplate;
    private final AspectUtils aspectUtils;
    private final ObjectMapper objectMapper;

    @Around("execution(public * *(..)) && @annotation(com.specific.group.currency.client.cache.annotation.RedisReactiveCacheAdd)")
    public Object redisReactiveCacheAdd(ProceedingJoinPoint joinPoint) {
        Method method = aspectUtils.getMethod(joinPoint);
        Class<?> returnType = method.getReturnType();
        RedisReactiveCacheAdd annotation = method.getAnnotation(RedisReactiveCacheAdd.class);
        String key = aspectUtils.getKeyVal(joinPoint, annotation.key(), annotation.useArgsHash());
        log.info("Evaluated Redis cacheKey: " + key);
        if (returnType.isAssignableFrom(Mono.class)) {
            return methodMonoResponseToCache(joinPoint, key);
        } else if (returnType.isAssignableFrom(Flux.class)) {
            return methodFluxResponseToCache(joinPoint, key);
        }
        throw new RuntimeException("RedisReactiveCacheAdd: Annotated method has unsupported return type, expected Mono<?> or Flux<?>");
    }


    @Around("execution(public * *(..)) && @annotation(com.specific.group.currency.client.cache.annotation.RedisReactiveCacheGet)")
    public Object redisReactiveCacheGet(ProceedingJoinPoint joinPoint) {
        Method method = aspectUtils.getMethod(joinPoint);
        Class<?> rawReturnType = method.getReturnType();
        RedisReactiveCacheGet annotation = method.getAnnotation(RedisReactiveCacheGet.class);
        String key = aspectUtils.getKeyVal(joinPoint, annotation.key(), annotation.useArgsHash());
        TypeReference typeRefForMapper = aspectUtils.getTypeReference(method);
        log.info("Evaluated Redis cacheKey: " + key);
        if (rawReturnType.isAssignableFrom(Mono.class)) {
            return reactiveRedisTemplate.opsForValue().get(key).map(cacheResponse ->
                            objectMapper.convertValue(cacheResponse, typeRefForMapper))
                    .switchIfEmpty(Mono.defer(() -> methodMonoResponseToCache(joinPoint, key)));
        } else if (rawReturnType.isAssignableFrom(Flux.class)) {
            return reactiveRedisTemplate.opsForValue().get(key)
                    .flatMapMany(cacheResponse -> Flux.fromIterable(
                            (List) ((List) cacheResponse).stream()
                                    .map(elem -> objectMapper.convertValue(elem, typeRefForMapper))
                                    .collect(Collectors.toList())))
                    .switchIfEmpty(Flux.defer(() -> methodFluxResponseToCache(joinPoint, key)));
        }
        throw new RuntimeException("RedisReactiveCacheGet: Annotated method has unsupported return type, expected Mono<?> or Flux<?>");
    }

    @Around("execution(public * *(..)) && @annotation(com.specific.group.currency.client.cache.annotation.RedisReactiveCacheEvict)")
    public Object redisReactiveCacheEvict(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = aspectUtils.getMethod(joinPoint);
        Class<?> returnType = method.getReturnType();
        RedisReactiveCacheEvict annotation = method.getAnnotation(RedisReactiveCacheEvict.class);
        String key = aspectUtils.getKeyVal(joinPoint, annotation.key(), annotation.useArgsHash());
        log.info("Evaluated Redis cacheKey: " + key);
        reactiveRedisTemplate.opsForValue().delete(key).subscribe();
        return joinPoint.proceed(joinPoint.getArgs());
    }

    private Mono<?> methodMonoResponseToCache(ProceedingJoinPoint joinPoint, String key) {
        try {
            return ((Mono<?>) joinPoint.proceed(joinPoint.getArgs())).map(methodResponse -> {
                reactiveRedisTemplate.opsForValue().set(key, methodResponse).subscribe();
                return methodResponse;
            });
        } catch (Throwable e) {
            return Mono.error(e);
        }
    }

    private Flux<?> methodFluxResponseToCache(ProceedingJoinPoint joinPoint, String key) {
        try {
            return ((Flux<?>) joinPoint.proceed(joinPoint.getArgs())).collectList().map(methodResponseList -> {
                reactiveRedisTemplate.opsForValue().set(key, methodResponseList).subscribe();
                return methodResponseList;
            }).flatMapMany(Flux::fromIterable);
        } catch (Throwable e) {
            return Flux.error(e);
        }
    }
}
