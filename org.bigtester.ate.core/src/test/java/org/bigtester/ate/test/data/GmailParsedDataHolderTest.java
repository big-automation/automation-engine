package org.bigtester.ate.test.data;

import org.bigtester.ate.model.data.GmailParsedDataHolder;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.testng.annotations.Test;

public class GmailParsedDataHolderTest {
	@Test
	public void test1() {
		@SuppressWarnings("null")
		GmailParsedDataHolder parser = new GmailParsedDataHolder(null, null);
		try {
			parser.parseData();
		} catch (RuntimeDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
