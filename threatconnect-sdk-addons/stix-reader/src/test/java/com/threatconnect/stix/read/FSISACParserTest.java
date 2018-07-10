package com.threatconnect.stix.read;

import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.parser.ParserException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FSISACParserTest extends AbstractParserTest
{
	@Test
	public void ParseFSISAC1() throws ParserException
	{
		// parse the list of items
		List<Item> items = parse("stix-fsisac1.xml");
		
		// make sure the list of items contains 3 documents
		Assert.assertEquals(3, items.size());
		for (int i = 0; i < 3; i++)
		{
			Assert.assertEquals(ItemType.INDICATOR, items.get(i).getItemType());
			Assert.assertEquals(File.INDICATOR_TYPE, ((Indicator) items.get(i)).getIndicatorType());
		}
		
		File file1 = (File) items.get(0);
		Assert.assertEquals("a0e3fa326fd761e0f76b27078882937a", file1.getMd5());
		Assert.assertEquals("c865ce28a6640c9614f0344e5b7d69b7651a1586", file1.getSha1());
		Assert.assertEquals("8989c590c8acc8fe65de858cb04c608d598161ea6c271d38b343ea6779ccc458", file1.getSha256());
		
		File file2 = (File) items.get(1);
		Assert.assertEquals("0976d77351c1cec418977083d9f0bc40", file2.getMd5());
		Assert.assertEquals("1c6e89531391095cd7a5fe6d14f126c9c20e2dba", file2.getSha1());
		Assert.assertEquals("d7945cfcd19ffbdd23cd3be908b1216fd6b923b52e54aedd1f526a2ca6a2e46b", file2.getSha256());
		
		File file3 = (File) items.get(2);
		Assert.assertEquals("54e10ad6ebbedcb221aded5d9f0c8f3f", file3.getMd5());
		Assert.assertEquals("642ccb4e8d5963daa1f710200d997faad1ce5005", file3.getSha1());
		Assert.assertEquals("adda2095d8c43424a50d8e6887babd21263dafcf7a7acf57a92a547ae210bb0d", file3.getSha256());
	}
}
