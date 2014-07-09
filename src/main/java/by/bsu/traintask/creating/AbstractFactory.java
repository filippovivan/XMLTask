package by.bsu.traintask.creating;

import by.bsu.traintask.exceptions.LogicalException;
import by.bsu.traintask.exceptions.TechnicalException;

public interface AbstractFactory<T> {
	T createInstance() throws TechnicalException, LogicalException;
}
