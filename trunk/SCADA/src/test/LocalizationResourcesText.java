package test;

import com.umbrella.scada.view.localization.LocalizationResources;

import junit.framework.TestCase;

public class LocalizationResourcesText extends TestCase {

	private LocalizationResources res;
	
	public LocalizationResourcesText(String name) {
		super(name);
		res = new LocalizationResources();
	}

	public void testGetLocal() {
		String hola = res.getLocal("Hola", LocalizationResources.SPANISHLOCALE);
		System.out.println(hola);
		//fail("Not yet implemented");
	}

}
