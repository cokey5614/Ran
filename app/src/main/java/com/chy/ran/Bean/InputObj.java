package com.chy.ran.Bean;


public class InputObj
{
	private static final int TP_OPERATION       = 0;
	public static final int TP_INPUT2APP       = 1;

	public void freshOutput(int tp, String[] tpo)
	{
		this.tp = tp;
		this.tpo = tpo;
	}

	public int getTp() {
		return tp;
	}
	public String[] getTpo() {
		return tpo;
	}

	private int tp = TP_OPERATION;
	private String[] tpo = null;

}