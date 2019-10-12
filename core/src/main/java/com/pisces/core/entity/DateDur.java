package com.pisces.core.entity;

import java.util.AbstractMap;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pisces.core.converter.DurationDeserializer;
import com.pisces.core.converter.DurationSerializer;
import com.pisces.core.utils.DateUtils;

@JsonSerialize(using = DurationSerializer.class)
@JsonDeserialize(using = DurationDeserializer.class)
public class DateDur {
	private static Logger log = LoggerFactory.getLogger(DateDur.class);
	enum  EV_STRING_TYPE {
		EVS_GOOD,
		EVS_HAVE_P,
	};
	class EV_STRING implements Cloneable {
		EV_STRING_TYPE type;
		String value = "";
		
		public EV_STRING clone() {
			EV_STRING o = null;
			try {
				o = (EV_STRING)super.clone();
			} catch (CloneNotSupportedException e) {
				log.error("error",e);
			}
			
			return o;
		}
	};
	
	public static final double PRECISION = 0.00001;
	public static final DateDur INVALID = new DateDur("");
	private String value;
	private int time = 0;
	private double rate = 0.0;
	private boolean bValid = false;
	
	public DateDur(String value) {
		setValue(value);
	}
	
	public void setValue(String value) {
		this.value = value == null ? "" : value;
		this.time = 0;
		this.rate = 0.0f;
		parse();
	}
	
	public String getValue() {
		return this.value;
	}
	
	public boolean Valid() {
		return this.bValid;
	}
	
	public int getTime() {
		return this.time;
	}
	
    public void setTime(int time) {
        this.time = time;
    }
	
	public long getTimeInMillis() {
		return this.time * DateUtils.PER_SECOND;
	}
	
	public String getString() {
		if (this.rate > DateDur.PRECISION)
			return this.getValue();
		
		if (this.time < 0) {
			return "";
		}
		if (this.time == 0) {
			return "0S";
		}

		int iMaxTimeUnit = 4;
		StringBuffer strExtraString = new StringBuffer();
		int extraTime = this.time;
		if (iMaxTimeUnit >= 4) {
			int i = extraTime / 86400;
			if (i > 0) {
				strExtraString.append(i).append("D");
			}
			extraTime -= i * 86400;
		}
		if (iMaxTimeUnit >= 3) {
			int i = extraTime / 3600;
			if (i > 0) {
				strExtraString.append(i).append("H");
			}
			extraTime -= i * 3600;
		}
		if (iMaxTimeUnit >= 2) {
			int i = extraTime / 60;
			if (i > 0) {
				strExtraString.append(i).append("M");
			}
			extraTime -= i * 60;
		}
		if (iMaxTimeUnit >= 1) {
			if (extraTime > 0) {
				strExtraString.append(extraTime).append("S");
			}
		}
		return strExtraString.toString();
	}
	
	public void Divide(double val) {
		if (val < PRECISION) {
			return;
		}
		if (this.time >= 0)
			this.time /= val;
		this.rate  /=  val;
		this.value = getString();
	}
	
	public long getTime(double amount) {
		if (amount < PRECISION)
			return getTime();
		
		double result = (amount * this.rate) + this.time;
		return (long)Math.ceil(result);
	}
	
	public long getTimeInMillis(double amount) {
		return this.getTime(amount) * DateUtils.PER_SECOND;
	}
	
	private void parse() {
		this.bValid = true;
		if (this.value.isEmpty() || this.value.equals("-1")) {
			this.time = 0;
			this.rate = 0.0f;
			this.bValid = false;
			return;
		}
		
		if (!ValueValid()) {
			this.time = 0;
			this.rate = 0.0f;
			this.bValid = false;
			return;
		}
		
		ArrayList<EV_STRING> lst = new ArrayList<DateDur.EV_STRING>();
		Decompound(lst);
		
		boolean oFlg = lst.size() == 1;

		for (EV_STRING evs : lst) {
			if (evs.type == EV_STRING_TYPE.EVS_GOOD)
			{
				AbstractMap.SimpleEntry<Boolean, Integer> extraTime = TransTime_Each(evs.value);
				if (extraTime.getKey() || !oFlg)
				{
					this.time += extraTime.getValue();
				}
				else
				{
					this.time += extraTime.getValue() * 1;
				}
			}
			else
			{
				AbstractMap.SimpleEntry<Boolean, Double> extraPer = TransRate_Each(evs.value);
				if (extraPer.getKey())
				{
					this.rate = extraPer.getValue();
				}
			}
		}
		
		if (this.time < 0)
		{
			this.time = 0;
			this.rate = 0.0f;
			this.bValid = false;
		}
	}
	
	private boolean ValueValid() {
		for (char ch : this.value.toCharArray()) {
			if (ch <= 'z' && ch >= 'a') {
				ch -= 32;
			}
			
			if (!isNumber(ch) && !isUnits(ch)) {
				return false;
			}
		}
		
		this.value = this.value.toUpperCase();
		return true;
	}
	
	private boolean isNumber(char ch) {
		return (ch <= '9' && ch >= '0') || (ch == '.');
	}
	
	private boolean isUnits(char ch) {
		return (ch == 'D' || ch == 'H'
				|| ch == 'M' || ch == 'S' || ch == '+'||ch == '-'||ch == 'P');
	}
	
	private void Decompound(ArrayList<EV_STRING> lst) {
		int len = this.value.length();
		int iStr = 0;
		EV_STRING evs = new EV_STRING();
		evs.type = EV_STRING_TYPE.EVS_GOOD;
		while(iStr < len)
		{
			char ch = this.value.charAt(iStr);
			if (ch == '+')
			{
				lst.add(evs.clone());
				evs.value = "";
				evs.type = EV_STRING_TYPE.EVS_GOOD;
				iStr++;
				continue;
			}
			else if (ch == '-')
			{
				int iPre = iStr;
				if (iPre != 0)
				{
					iPre--;
					char cp = this.value.charAt(iPre);
					if (!(cp == 'D' || cp == 'H'
						|| cp == 'M' || cp == 'S'))
					{
						evs.value = evs.value + 'S';
					}
				}
			}
			else if (ch == 'P')
			{
				int iTempIndex = 0;
				StringBuffer temp1 = new StringBuffer();
				StringBuffer temp2 = new StringBuffer();
				int iTemp = evs.value.length() - 1;
				for (;iTemp >= 0;iTemp--)
				{
					char cTemp = evs.value.charAt(iTemp);
					if (cTemp == 'D' || cTemp == 'H'
						|| cTemp == 'M' || cTemp == 'S')
					{
						iTempIndex++;
					}
					if (iTempIndex >= 2)
					{
						temp1.insert(0,cTemp);
					}
					else
					{
						temp2.insert(0,cTemp);
					}
				}
				if (!temp1.toString().isEmpty())
				{
					EV_STRING evs1 = new EV_STRING();
					evs1.type = EV_STRING_TYPE.EVS_GOOD;
					evs1.value = temp1.toString();
					lst.add(evs1);
				}
				evs.value = temp2.toString();
				evs.type = EV_STRING_TYPE.EVS_HAVE_P;
				evs.value = evs.value + ch;
				iStr++;
				if (iStr != len)
				{
					char cp = this.value.charAt(iStr);
					if (cp == 'D' || cp == 'H'
						|| cp == 'M' || cp == 'S')
					{
						evs.value = evs.value + cp;
						iStr++;
					}
				}
				
				lst.add(evs.clone());
				evs.value = "";
				evs.type = EV_STRING_TYPE.EVS_GOOD;
				continue;
			}
			evs.value = evs.value + ch;
			iStr++;
		}
		if (!evs.value.isEmpty())
		{
			lst.add(evs);
		}
	}
	
	private AbstractMap.SimpleEntry<Boolean, Integer> TransTime_Each(String extraValue) {
		if (extraValue.isEmpty())
			return new AbstractMap.SimpleEntry<Boolean, Integer>(false, 0);

		int extraTime = 0;
		StringBuffer temp = new StringBuffer();
		boolean flg = false;
		for (int i = 0; i < extraValue.length(); i++) {
			char ch = extraValue.charAt(i);
			int rate = TimeUnit(ch);
			if (rate > 0)
			{
				double iC = Double.parseDouble(temp.toString());
				extraTime += (int)(iC*rate);
				temp.setLength(0);
				flg = true;
			}
			else
			{
				temp.append(ch);
			}
		}
		if (!temp.toString().isEmpty())
			extraTime += Double.parseDouble(temp.toString());
		return new AbstractMap.SimpleEntry<Boolean, Integer>(flg, extraTime);
	}
	private AbstractMap.SimpleEntry<Boolean, Double> TransRate_Each(String extraValue) {
		if (extraValue.isEmpty())
			return new AbstractMap.SimpleEntry<Boolean, Double>(false, 0.0);

		double extraPer = 0.0;
		StringBuffer temp = new StringBuffer();
		boolean flg = false;
		for (int i = 0; i < extraValue.length(); i++) {
			char ch = extraValue.charAt(i);
			switch(ch)
			{
			case 'D': extraPer = flg ?  (1 / Double.parseDouble(temp.toString())) * 86400  : Double.parseDouble(temp.toString()) * 86400; return new AbstractMap.SimpleEntry<Boolean, Double>(true, extraPer);
			case 'H': extraPer = flg ?  (1 / Double.parseDouble(temp.toString())) * 3600: Double.parseDouble(temp.toString()) * 3600; return new AbstractMap.SimpleEntry<Boolean, Double>(true, extraPer);
			case 'M': extraPer = flg ?  (1 / Double.parseDouble(temp.toString())) * 60 : Double.parseDouble(temp.toString()) * 60; return new AbstractMap.SimpleEntry<Boolean, Double>(true, extraPer);
			case 'S': extraPer = flg ?  (1 / Double.parseDouble(temp.toString())) * 1 : Double.parseDouble(temp.toString()) * 1; return new AbstractMap.SimpleEntry<Boolean, Double>(true, extraPer);
			case 'P': flg = true;break;
			default  : temp.append(ch);
			}
		}
		return new AbstractMap.SimpleEntry<Boolean, Double>(true, extraPer);
	}
	
	private int TimeUnit(char ch) {
		switch(ch)
		{
		case 'D': return 86400;
		case 'H': return 3600;
		case 'M': return 60;
		case 'S': return 1;
		}
		return 0;
	}
}
