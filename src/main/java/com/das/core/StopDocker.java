package com.das.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.testng.asserts.SoftAssert;

public class StopDocker {

	public void stopDockerGrid(String dockerValue) throws IOException, InterruptedException {
		Thread.sleep(60000);
		boolean flag = false;
		Runtime runtime = Runtime.getRuntime();
		if (SystemUtils.OS_NAME.contains("Windows")) {
			runtime.exec("cmd /c start DockerDown.bat");
		} else if (SystemUtils.OS_NAME.contains("Linux")) {
			runtime.exec("sh start DockerDown.bat");
		}

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
			String currentLine = reader.readLine().replaceAll("\\s", "");
			while (currentLine != null && !flag) {
				if (currentLine.contains("selenium-hub[32mRemoved[0m[34m")
						|| currentLine.contains("selenium-hubRemoved")) {
					System.out.println("found my text" + currentLine);
					flag = true;// 14th seconds
					Thread.sleep(5000);
					break;
				}
				currentLine = reader.readLine().replaceAll("\\s", "");
			}
			reader.close();

		}

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(flag);

		File file = new File("OutputLog.txt");
		file.delete();

		File file2 = new File("ShutDownLog.txt");
		file2.delete();
		if (!StringUtils.trim(dockerValue).equalsIgnoreCase("true")) {
			if (SystemUtils.OS_NAME.contains("Windows")) {
				runtime.exec("cmd /c taskkill /F /IM cmd.exe /T");
			} else if (SystemUtils.OS_NAME.contains("Linux")) {
				// runtime.exec("sh taskkill \\F \\IM cmd.exe \\T");
			}
		}

	}

}
