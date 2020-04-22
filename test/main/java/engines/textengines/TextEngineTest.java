package main.java.engines.textengines;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

class TextEngineTest {

	
	protected String getContentFromFile(Path filePath) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath.toString()), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		br.lines().forEach(sb::append);
		br.close();
		return sb.toString();
	}

}
