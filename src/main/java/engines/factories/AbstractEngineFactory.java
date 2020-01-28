package main.java.engines.factories;

import main.java.engines.AbstractEngine;

import java.util.List;

public abstract class AbstractEngineFactory {

    protected abstract AbstractEngine createEngine(List<String> inputParam);
}
