package com.idsargus.akpmsadminservice.event;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {

	@Override
	public void onEvent(CacheEvent<?, ?> cacheEvent) {
		System.out.println("Cached Entity ..........................................");
		log.debug("Key: {} | EventType: {} | Old value: {} | New value: {}", cacheEvent.getKey(), cacheEvent.getType(),
				cacheEvent.getOldValue(), cacheEvent.getNewValue());
	}

}
