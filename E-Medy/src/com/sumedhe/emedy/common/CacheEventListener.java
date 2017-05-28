package com.sumedhe.emedy.common;

import java.util.EventListener;
import java.util.EventObject;

public interface CacheEventListener extends EventListener {
	void updated(EventObject e);
}
