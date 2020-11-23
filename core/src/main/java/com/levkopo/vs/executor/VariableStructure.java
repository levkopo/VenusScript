package com.levkopo.vs.executor;

import com.levkopo.vs.value.Value;

import java.util.ArrayList;
import java.util.List;

public class VariableStructure {
	private final List<Object> changeMonitors;
	private final Object lockMonitor;
	private Value value;

	public VariableStructure(Value value) {
		this.changeMonitors = new ArrayList<>();
		this.lockMonitor = new Object();
		this.value = value;
	}

	public void addChangeMonitor(Object monitor) {
		synchronized (changeMonitors) {
			changeMonitors.add(monitor);
		}
	}

	public Object getLockMonitor() {
		return lockMonitor;
	}

	public Value getValue() {
		return value;
	}

	public void removeChangeMonitor(Object monitor) {
		synchronized (changeMonitors) {
			changeMonitors.remove(monitor);
		}
	}

	public void setValue(Value value) {
		this.value = value;

		synchronized (changeMonitors) {
			for (Object monitor : changeMonitors) {
				synchronized (monitor) {
					monitor.notifyAll();
				}
			}
		}
	}

	@Override
	public String toString() {
		return getValue().toString();
	}
}