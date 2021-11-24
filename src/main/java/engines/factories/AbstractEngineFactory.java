package main.java.engines.factories;

import java.util.List;

import main.java.engines.AnkiEngine;

public abstract class AbstractEngineFactory {

    protected abstract AnkiEngine createEngine(List<String> inputParam);
}
