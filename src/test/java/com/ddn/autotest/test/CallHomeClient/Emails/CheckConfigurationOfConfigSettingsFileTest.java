package com.ddn.autotest.test.CallHomeClient.Emails;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.utils.StringUtils;
import com.ddn.autotest.utils.settings.Settings;
import com.ddn.autotest.utils.settings.SettingsKeys;

public class CheckConfigurationOfConfigSettingsFileTest extends CallHomeTestBase
{
	
	@Test ( description = "Check configuration Of Config Settings file",  groups = "CHEmail" )
	public void TC9052() throws Exception
	{
		Settings.setProperty(SettingsKeys.CHC_SETTINGS_LOG_FILE, "/etc/ddn/directmon/callhome/logging.conf");

		File f = new File(Settings.getProperty(SettingsKeys.CHC_SETTINGS_LOG_FILE));
		
		String chcDir = ssh.runCommand("ls " + StringUtils.toUnixSeparator(f.getParent()) + "/");
		Assert.assertTrue(chcDir.contains(f.getName()));
	
		String configContent = ssh.runCommand("cat " + Settings.getProperty(SettingsKeys.CHC_SETTINGS_LOG_FILE));
		Assert.assertTrue(configContent.contains("logfile="), "logfile line expected");
		Assert.assertTrue(configContent.contains("level="), "level line expected");
		Assert.assertTrue(configContent.contains("format="), "format line expected");
		Assert.assertTrue(configContent.contains("datefmt="), "datefmt line expected");
		Assert.assertTrue(configContent.contains("maxBytes="), "maxBytes line expected");
		Assert.assertTrue(configContent.contains("backupCount="), "backupCount line expected");
	}
}
