package com.levkopo.vs.executor;

import com.levkopo.vs.component.*;
import com.levkopo.vs.component.branch.*;
import com.levkopo.vs.exception.runtime.InvalidValueTypeException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.expression.Expression;
import com.levkopo.vs.function.Definition;
import com.levkopo.vs.origin.ScriptMode;
import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.value.*;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Supplier;

public class VenusExecutor {
	private final Queue<ScriptRuntimeException> asyncExceptions;
	private boolean breaking;
	private boolean continuing;
	private boolean shouldRun;

	public VenusExecutor() {
		this.asyncExceptions = new ArrayDeque<>();
		this.shouldRun = true;
	}

	public Value run(Container container, ScriptMode mode) throws ScriptRuntimeException {
		return run(container, mode, () -> shouldRun);
	}

	public void stop() {
		this.shouldRun = false;
	}

	protected Value run(Container container, ScriptMode mode, Supplier<Boolean> shouldRun) throws ScriptRuntimeException {
		Context context = container.getContext();
		Iterator<Component> iterator = container.getChildren().iterator();
		Value result = null;
		boolean hadIfAndNotProceed = false;

		container.getApplicationContext().setExecutor(this);

		while (shouldRun.get() && iterator.hasNext()) {
			Component component = iterator.next();

			if (breaking || continuing) {
				break;
			}

			if (!asyncExceptions.isEmpty()) {
				throw asyncExceptions.poll();
			}

			container.getApplicationContext().setCurrentLine(component.getSourceLine());

			if (component instanceof Container) {
				if (component instanceof AsyncContainer) {
					/*AsyncContainer asyncContainer = (AsyncContainer) component;
					IgThread thread = new IgThread("AsyncVenusThread", () -> {
						VenusExecutor executor = new VenusExecutor();

						try {
							executor.run(asyncContainer, mode, () -> VenusExecutor.this.shouldRun);
						} catch (ScriptRuntimeException exception) {
							asyncExceptions.push(exception);
						} catch (Exception exception) {
							XLogger.println(exception);
						}
					});

					asyncThreads.add(thread);
					thread.setDaemon(asyncContainer.isDaemon());
					thread.start();*/
				} else if (component instanceof ForEachContainer) {
					ForEachContainer forContainer = (ForEachContainer) component;
					Expression expression = forContainer.getIterable();
					Value value = expression.resolve(context, context);

					if (value instanceof IterableValue) {
						IterableValue iterable = (IterableValue) value;

						for (Value element : iterable) {
							context.setVar(forContainer.getVarName(), element);
							result = run(forContainer, mode, shouldRun);

							if(result!=null)
								return result;

							if (breaking) {
								this.breaking = false;

								break;
							}

							if (continuing) {
								this.continuing = false;
							}
						}
					} else {
						throw new InvalidValueTypeException(context, "For value \"" + value + "\" is not iterable");
					}
				} else if (component instanceof ForRangeContainer) {
					ForRangeContainer forContainer = (ForRangeContainer) component;
					Value from = forContainer.getFrom().resolve(context, context);
					Value to = forContainer.getTo().resolve(context, context);

					if (from instanceof NumericValue) {
						if (to instanceof NumericValue) {
							NumericValue numericFrom = (NumericValue) from;
							NumericValue numericTo = (NumericValue) to;
							boolean isDecimal = numericFrom instanceof DecimalValue || numericTo instanceof DecimalValue;
							Value count = isDecimal ? new DecimalValue(numericFrom.value().doubleValue()) : new IntegerValue(numericFrom.value().longValue());

							while (count.lowerEqualThan(to).value().equals(true)) {
								context.setVar(forContainer.getVarName(), count);
								result = run(forContainer, mode, shouldRun);

								if(result!=null)
									return result;

								if (breaking) {
									this.breaking = false;

									break;
								}

								if (continuing) {
									this.continuing = false;
								}

								count = forContainer.getAdjustment().resolve(context, context);
							}
						} else {
							throw new InvalidValueTypeException(context, "For end \"" + to + "\" is not a numeric value");
						}
					} else {
						throw new InvalidValueTypeException(context, "For start \"" + from + "\" is not a numeric value");
					}
				} else if (component instanceof WhileContainer) {
					WhileContainer whileContainer = (WhileContainer) component;

					while (true) {
						Value value = whileContainer.getCondition().resolve(context, context);

						if (value instanceof BoolValue) {
							BoolValue boolValue = (BoolValue) value;

							if (boolValue.value()) {
								run(whileContainer, mode, shouldRun);

								if (breaking) {
									this.breaking = false;

									break;
								}

								if (continuing) {
									this.continuing = false;
								}
							} else {
								break;
							}
						} else {
							throw new InvalidValueTypeException(context, "Cannot apply while condition in value of type " + value.getType());
						}
					}
				} else if (component instanceof DoWhileContainer) {
					DoWhileContainer doWhileContainer = (DoWhileContainer) component;

					while (true) {
						run(doWhileContainer, mode, shouldRun);

						if (breaking) {
							this.breaking = false;

							break;
						}

						if (continuing) {
							this.continuing = false;
						}

						if (!doWhileContainer.hasCondition()) {
							break;
						}

						Value value = doWhileContainer.getCondition().resolve(context, context);

						if (value instanceof BoolValue) {
							BoolValue boolValue = (BoolValue) value;

							if (!boolValue.value()) {
								break;
							}
						} else {
							throw new InvalidValueTypeException(context, "Cannot apply while condition in value of type " + value.getType());
						}
					}
				} else if (component instanceof IfContainer) {
					IfContainer ifContainer = (IfContainer) component;
					Value value = ifContainer.getCondition().resolve(context, context);

					if (value instanceof BoolValue) {
						BoolValue boolValue = (BoolValue) value;

						if (boolValue.value()) {
							result = run(ifContainer, mode, shouldRun);
						} else {
							hadIfAndNotProceed = true;

							continue;
						}
					} else {
						throw new InvalidValueTypeException(context, "Cannot apply if condition in value of type " + value.getType());
					}
				} else if (component instanceof ElseContainer) {
					if (hadIfAndNotProceed) {
						run((Container) component, mode, shouldRun);
					}
				} else if (!(component instanceof Definition)) {
					run((Container) component, mode, shouldRun);
				}
			} else if (component instanceof Break) {
				this.breaking = true;
			} else if (component instanceof Continue) {
				this.continuing = true;
			} else if (component instanceof Return) {
				Return returner = (Return) component;

				if (returner.getExpression() != null) {
					Value value = returner.getExpression().resolve(context, context);
					Container c = returner.getParent();
					while(!(c instanceof Script)){
						if(c instanceof Definition&&!value.getType().equals(PrimitiveType.ANY)){
							Definition definition = (Definition) c;
							if(definition.getReturnType().equals(PrimitiveType.VOID))
								throw new InvalidValueTypeException(context, "Void cannot return values");

							if(!definition.getReturnType().equals(value.getType())
									&&!definition.getReturnType().equals(PrimitiveType.ANY))
								throw new InvalidValueTypeException(context,  "The definition was expected to return type of "+definition.getReturnType().getIdentifier());

							break;
						}

						c = c.getParent();
						if(c==null)
							break;
					}

					result = value;
				}

				break;
			} else if (component instanceof SimpleComponent) {
				SimpleComponent simple = (SimpleComponent) component;
				Value value = simple.getExpression().resolve(context, context);

				if (value != null) {
					if (mode == ScriptMode.EVALUATION) {
						result = value;
					} else if (mode == ScriptMode.INTERACTIVE) {
						result = value;
						System.out.println(value);
					}
				}
			}

			if(result!=null)
				return result;

			hadIfAndNotProceed = false;
		}

		//asyncThreads.join(); // TODO Bugged. Removed temporarily.

		if (!asyncExceptions.isEmpty()) {
			throw asyncExceptions.poll();
		}

		return result;
	}
}
