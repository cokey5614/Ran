package com.chy.ran.Manager;

import com.chy.ran.Util.AndroidFileReader;
import com.chy.ran.Util.IOWatcher;
import com.chy.ran.Util.LogUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ArcImeCoreManager {

	private static anlysizeInput anlysizer = null;

    public static ArcImeCoreManager getInstance() {
        return new ArcImeCoreManager();
    }
    
    public boolean init()
    {
        listCandidate = new String[anlysizeInput.LIST_CANDIDATE_LENGTH];
        anlysizer = new anlysizeInput();
        anlysizer.init();
        return true;
    }

    public boolean processRequest(String pinyin){
        inputStr = pinyin;
        if (!anlysizer.inputCheck()) return false;
//        inputChar = String.valueOf(input.charAt(0));
        ArcImeCoreManager.anlysizer.anlysize();
        
        return true;
    }

    public String[] answerResponse(){
        return listCandidate;
    }

	public int getIndexOfCandicateSnapToInputView() {
		return this.indexOfCandicateSnapToInputView;
	}


    private class anlysizeInput
    {
        public static final int INPUT_CHARS_LENGTH    = 32;
        public static final int LIST_CANDIDATE_LENGTH = 512;

        private HashMap hashMap = null;//<A..Z, linkedhashmap<pinyin, hanzi...hanzi>>
        public anlysizeInput(){}
        public boolean init()
        {
            LogUtil.d(this.getClass().getName(), "begin init");

            hashMap = new HashMap();//<A..Z, linkedhashmap<pinyin, hanzi...hanzi>>
            //IOWatcher io = getIOWatcher();// 简化版工厂模式，快速无感知切换测试和工程版本
            IOWatcher io = new AndroidFileReader();
            io.readLiner(hashMap);

            LogUtil.d(this.getClass().getName(), "end init");
            
			return true;
        }

        private IOWatcher getIOWatcher() {
			return new AndroidFileReader();
		}
		public boolean inputCheck()
        {
            if(inputStr == null || inputStr.length() == 0) return false;
            return true;
        }
        public boolean anlysize()
        {
            for (int i = 0; i < listCandidate.length && null != listCandidate[i]; i++)
            {
                 listCandidate[i] = "";
            }

            anlysize_level2();

            return true;
        }

        public boolean anlysize_level2()
        {
            indexOfCandicateSnapToInputView = 0;

            return anlysize_level3_recursionEncode(inputStr.length());
        }

        public boolean anlysize_level3_recursionEncode(int curLastCharPostion)//<String, String>
        {            
            String value = (String) getCandicate(curLastCharPostion);
            if (null == value){
                if (1 == curLastCharPostion){return false;}//没必要再搞了
                return anlysize_level3_recursionEncode(curLastCharPostion - 1);
            }
            else
            {
                int iCandidateLen = -1;
                String[] valuelst = value.split(",");
                for(int i = 0; i < valuelst.length; i++){
                    if((++iCandidateLen) < LIST_CANDIDATE_LENGTH) listCandidate[iCandidateLen] = valuelst[i];
                    if (iCandidateLen == LIST_CANDIDATE_LENGTH - 1) return true;
                }
                indexOfCandicateSnapToInputView = curLastCharPostion;// index from 1
                return true;
            }
        }
        
		private String getCandicate(int curLastCharPostion) {
            if (0 == curLastCharPostion) return null;

        	LinkedHashMap linkedHashMap = (LinkedHashMap)(hashMap.get(String.valueOf(inputStr.charAt(0))));

            if (null == linkedHashMap) return null;

            // in case of exception
        	//if (curLastCharPostion >= inputStr.length()) return (String)(linkedHashMap.get(inputStr.substring(0)));

            String tmpInputs = (curLastCharPostion >= inputStr.length()) ?
                    (inputStr.substring(0))
                    : (inputStr.substring(0, curLastCharPostion));
            String tmpOutputs = (String)(linkedHashMap.get(tmpInputs));
            if (null == tmpOutputs)// case when cur pinyin chars not exist in dic, so need to fuzzy lookup
            {
                String resOutputs = "";
                Iterator iver = linkedHashMap.entrySet().iterator();
                while (iver.hasNext())
                {
                    Map.Entry entry = (Map.Entry) iver.next();
                    String key = (String)entry.getKey();
                    if(key.length() > curLastCharPostion + 1 && key.indexOf(tmpInputs) == 0)
                    {
                        if (resOutputs.indexOf((String)entry.getValue()+",") < 0)
                        {
                            resOutputs += (String)entry.getValue() + ",";
                        }
                    }
                }
                return "".equals(resOutputs) ? null : resOutputs;
            }
            else
            {
                return tmpOutputs;
            }
		}

    }

    private int indexOfCandicateSnapToInputView = 0;//下标从1开始,
    private String inputStr     = null;
	private static String[] listCandidate = null;
}