package main.java.strategy;

import main.java.contracts.IParser;

public enum ReadingStrategy {

	GENERAL {
		@Override
		public String formatLine(String input) {
			return input;
		}
	},
	KINDLE {
		@Override
		public String formatLine(String input) {
			return IParser.replaceNewLines(input);
		}
	},
	LANGUAGE_LEARNING {
		@Override
		public String formatLine(String input) {
			return input + IParser.NEW_LINE;
		}
	}, // TODO - questo e' il pezzo che prima mi faceva il br. aggiustare sta roba.
	EVERNOTE {
		@Override
		public String formatLine(String input) {
			return input;
		}
	};

	abstract String formatLine(String input);

	public String format(String input) {
		return this.formatLine(input);
	}
}
