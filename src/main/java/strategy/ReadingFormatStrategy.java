package main.java.strategy;

import main.java.contracts.IParser;

public enum ReadingFormatStrategy {

	ADD_PIPE {
		@Override
		public String formatLine(String input) {
			if (input.trim().endsWith(IParser.PIPE_SEPARATOR))
				return input;
			return input + IParser.REGULAR_PIPE;
		}
	},
	REPLACE_NEW_LINES {
		@Override
		public String formatLine(String input) {
			return IParser.replaceNewLines(input);
		}
	},
	ADD_NEW_LINE { // TODO - questo e' il pezzo che prima mi faceva il br. aggiustare sta roba. Verificare se l'errore persiste ancora
		@Override
		public String formatLine(String input) {
			return input + IParser.NEW_LINE;
		}
	},
	NO_FORMAT {
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
