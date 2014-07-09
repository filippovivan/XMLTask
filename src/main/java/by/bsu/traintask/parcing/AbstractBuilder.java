package by.bsu.traintask.parcing;

import by.bsu.traintask.exceptions.LogicalException;
import by.bsu.traintask.exceptions.TechnicalException;

public interface AbstractBuilder<T> {
	T createInstance() throws TechnicalException, LogicalException;
}
