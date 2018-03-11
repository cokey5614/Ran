package com.chy.ran.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class FilReader implements IOWatcher {
	public void readLiner(HashMap hashMap) {

        /**
         * a
         *   ai 哎
         *   ao 奥
         * b
         *   bi 比
         *   bo 波
         */
        LinkedHashMap linkedHashMap = null;//<String, String>

		//Toast.makeText(ranRanArcService, "ra2n:"+ranRanArcService == null ? "null" : "Done",Toast.LENGTH_LONG).showToToast();
		try {
			String pathname = "D:\\Work\\space\\arcime\\src\\main\\java\\com\\example\\shqx\\arcime\\dic.txt";
			File filename = new File(pathname);
			InputStreamReader reader = new InputStreamReader(
					new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			
			String line = "";
			line = br.readLine();
			String[] listline = line.split("\\|");
			String key = listline[0];
			String value = listline[1];
			linkedHashMap = new LinkedHashMap();
			linkedHashMap.put(key, value);
	        hashMap.put(String.valueOf(key.charAt(0)), linkedHashMap);
			
			while (line != null) {
				line = br.readLine();
				if (null == line) continue;
				
				listline = line.split("\\|");
				key = listline[0];
				value = listline[1];
				
				LinkedHashMap inner_linkedHashMap = (LinkedHashMap)(hashMap.get(String.valueOf(key.charAt(0))));
				if (null != inner_linkedHashMap)
				{
					inner_linkedHashMap.put(key, value);
				}
				else
				{
					inner_linkedHashMap = new LinkedHashMap();
					inner_linkedHashMap.put(key, value);
			        hashMap.put(String.valueOf(key.charAt(0)), inner_linkedHashMap);
				}
				
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
