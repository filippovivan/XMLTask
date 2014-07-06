package by.bsu.traintask.launching;

import org.apache.log4j.PropertyConfigurator;

public class Launcher {

	static {
		PropertyConfigurator.configure(Launcher.class
				.getResourceAsStream("/logconfig.properties"));
	}

	public static void main(String[] args) {
		
	}
}
