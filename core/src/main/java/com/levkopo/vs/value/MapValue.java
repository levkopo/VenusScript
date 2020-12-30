package com.levkopo.vs.value;

import com.levkopo.vs.type.PrimitiveType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

public class MapValue extends IterableValue{

	private final Map<Object, Value> map;

	public MapValue(Map<Object, Value> map) {
		super(PrimitiveType.MAP);
		this.map = map;
	}

	@Override
	public MapValue clone() {
		return new MapValue(new HashMap<>(map));
	}

	@Override
	public Iterator<Value> iterator() {
		return map.values().iterator();
	}

	@Override
	public void forEach(Consumer<? super Value> action) {
		map.values().forEach(action);
	}

	@Override
	public Spliterator<Value> spliterator() {
		return map.values().spliterator();
	}

	public Value get(Value index) {
		if (!map.containsKey(index.value())) {
			return new NullValue();
		}

		return map.get(index.value());
	}

	public Map<Object, Value> getMap() {
		return map;
	}

	@Override
	public Map<Object, Value> value() {
		return map;
	}

	@Override
	public String toString() {
		return map.toString();
	}
}
