package main.java.strategy;

import main.java.abstractModel.IParser;

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
			return input.replace("\n", "").trim();
		}
	},
	LANGUAGE_LEARNING {
		@Override
		public String formatLine(String input) {
			return input + IParser.NEW_LINE_HTML;
		}
	},
	EVERNOTE {
		@Override
		public String formatLine(String input) {
			return input;
		}
	}
	;
	
	 abstract String formatLine(String input);
	 
	 public String format(String input) {
		 return this.formatLine(input);
	 }
}
