package main.java.engines.factories;

import java.util.List;

import main.java.engines.AbstractEngine;

public abstract class AbstractEngineFactory {

    protected abstract AbstractEngine createEngine(List<String> inputParam);
}
