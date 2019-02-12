package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Reader {
	public static BufferedReader getReader(InputStream is) {
		return new BufferedReader(new InputStreamReader(is));
	}
}
