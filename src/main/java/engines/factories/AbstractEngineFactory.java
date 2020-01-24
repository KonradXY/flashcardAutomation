package main.java.engines.factories;

import main.java.engines.TextEngine;

import java.util.List;

public abstract class AbstractEngineFactory {

    protected abstract TextEngine createTextEngine(List<String> inputParam);
}
