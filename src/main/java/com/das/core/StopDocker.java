package com.das.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import org.testng.asserts.SoftAssert;

public class StopDocker {

	public void stopDockerGrid() throws IOException, InterruptedException {

		boolean flag = false;
		Runtime runtime = Runtime.getRuntime();

		System.out.println("Test Jenkins before docker compose down");
		runtime.exec("cmd /c start DockerDown.bat");
		System.out.println("Test Jenkins after docker compose down");

		String f = "ShutDownLog.txt";

		Calendar cal = Calendar.getInstance();// 2:44 15th second
		cal.add(Calendar.SECOND, 45);// 45seconds
		long stopnow = cal.getTimeInMillis();
		Thread.sleep(3000);

		while (System.currentTimeMillis() < stopnow) {
			if (flag) {
				break;
			}

			BufferedReader reader = new BufferedReader(new FileReader(f));
			String currentLine = reader.readLine();
			while (currentLine != null && !flag)

			{

				if (currentLine.contains("selenium-hub                          [32mRemoved[0m[34m")
						|| currentLine.contains("selenium-hub                 Removed")) {
					System.out.println("found my text");
					flag = true;// 14th seconds
					Thread.sleep(5000);
					break;
				}
				System.out.println(currentLine);
				currentLine = reader.readLine();
			}
			reader.close();

		}

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(flag);

		File file = new File("OutputLog.txt");
		file.delete();

		File file2 = new File("ShutDownLog.txt");
		file2.delete();
		runtime.exec("cmd /c taskkill /F /IM cmd.exe /T");
	}

}
