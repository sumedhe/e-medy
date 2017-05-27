package com.sumedhe.emedy.common;

import java.util.EventListener;
import java.util.EventObject;

public interface ValidatorEventListener extends EventListener {
	void check(EventObject e);
}
