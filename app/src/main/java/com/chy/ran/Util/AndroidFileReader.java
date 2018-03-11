package com.chy.ran.Util;

import com.chy.ran.R;
import com.chy.ran.Service.ArcServiceHYRHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AndroidFileReader implements IOWatcher {// thanks for	// http://outofmemory.cn // 永明

	public void readLiner(HashMap hashMap) {
        InputStream inStream = RanRanContext.getContext().getResources().openRawResource(R.raw.dic);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

		try {
            if (null == reader)
			{
                LogUtil.i(this.getClass().getName(), "reader is null");
				hashMap = new LinkedHashMap();
				return;
			}

			String line = reader.readLine();
			String[] listLine = line.split("\\|");

			String key = listLine[0];
			String value = listLine[1];

            LinkedHashMap linkedHashMap = new LinkedHashMap();
			linkedHashMap.put(key, value);
	        hashMap.put(String.valueOf(key.charAt(0)), linkedHashMap);//<A..Z, linkedhashmap<pinyin, hanzi...hanzi>>

			while (line != null) {
				line = reader.readLine();
				if (null == line) continue;

				listLine = line.split("\\|");
				key = listLine[0];
				value = listLine[1];

				LinkedHashMap inner_linkedHashMap = (LinkedHashMap)(hashMap.get(String.valueOf(key.charAt(0))));
				if (null != inner_linkedHashMap)
				{
					String tmpValue = (String)inner_linkedHashMap.get(key);
					if (tmpValue == null)
					{
						inner_linkedHashMap.put(key, value);
					}
					else
					{
						inner_linkedHashMap.put(key, value + "," + tmpValue);
					}
				}
				else
				{
					inner_linkedHashMap = new LinkedHashMap();
					inner_linkedHashMap.put(key, value);
			        hashMap.put(String.valueOf(key.charAt(0)), inner_linkedHashMap);
				}
			}
            LogUtil.i(this.getClass().getName(), "hashMap size=" + hashMap.entrySet().size());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
