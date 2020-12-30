package com.levkopo.vs.expression;

import com.levkopo.vs.component.object.Attribute;
import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.exception.runtime.UndefinedAttributeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.ObjectValue;
import com.levkopo.vs.value.Value;

import java.util.Map;

public class NewObject implements Expression {
	private final Map<String, Expression> attributes;
	private final String objectType;

	public NewObject(String objectType, Map<String, Expression> attributes) {
		this.attributes = attributes;
		this.objectType = objectType;
	}

	public Map<String, Expression> getAttributes() {
		return attributes;
	}

	public String getObjectType() {
		return objectType;
	}

	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		ObjectDefinition definition = context.getOwner().findObjectDefinition(context, getObjectType());
		Context c = new Context(definition, null); // See issue #24

		for (Map.Entry<String, Expression> pair : getAttributes().entrySet()) {
			Attribute attribute = null;
			for(Attribute attr: definition.getAttributes()){
				if(attr.getName().equals(pair.getKey())) {
					attribute = attr;
				}
			}

			if (attribute != null) {
				Value value = pair.getValue().resolve(context, vars_context);

				c.setVar(pair.getKey(), value);
			} else {
				throw new UndefinedAttributeException(context, definition, pair.getKey());
			}
		}

		for (Attribute attribute : definition.getAttributes()) {
			if (!c.hasVar(attribute.getName()) && attribute.hasDefaultExpression()) {
				c.setVar(attribute.getName(), attribute.getDefaultExpression().resolve(context, vars_context));
			}
		}

		ObjectValue instance = new ObjectValue(definition, c);
		c.setVar("this", instance);

		return instance;
	}

	@Override
	public String toString() {
		return "new(" + getObjectType() + " << " + getAttributes() + ')';
	}
}
