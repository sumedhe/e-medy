package com.sumedhe.emedy.tool;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.event.EventListenerList;

import com.sumedhe.emedy.common.CacheEventListener;

public class Cache<V> {
	Map<Integer, V> cache = new HashMap<Integer, V>();
	EventListenerList listenerList = new EventListenerList();
	boolean stopRefresh;
	
	public Cache (){
		
	}
	
	
	public void put(int key, V item){
		cache.put(key, item);
		refreshAll();
	}
	
	public V get(int key){
		return cache.get(key);
	}
	
	public void remove(int key){
		cache.remove(key);
		refreshAll();
	}
	
	public List<V> getItemList(){
		return new ArrayList<V>(cache.values());
	}
	
	public Stream<V> getStream(){
		return cache.values().stream();
	}
	
	public void clear(){
		cache.clear();
		refreshAll();
	}
	
	public void refreshAll(){
		if (!stopRefresh){
			CacheEventListener[] ls = listenerList.getListeners(CacheEventListener.class);
			for (CacheEventListener l : ls){
				l.updated(new EventObject(this));
			}			
		}
	}
	
	public boolean isEmpty(){
		return cache.keySet().size() == 0;
	}
	
	
	
	public void addCacheEventListener(CacheEventListener l){
		listenerList.add(CacheEventListener.class, l);
	}
	
	public void removeCacheEventListener(CacheEventListener l){
		listenerList.remove(CacheEventListener.class, l);
	}
	
	
	
	public void editingMode(boolean isEditing){
		stopRefresh = isEditing;
	}
}
