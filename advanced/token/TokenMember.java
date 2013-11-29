package com.chaosdev.textmodloader.advanced.token;

import java.util.Arrays;

import clashsoft.cslib.util.CSString;

import com.chaosdev.textmodloader.advanced.ClassData;
import com.chaosdev.textmodloader.advanced.members.Member;

public class TokenMember extends Token
{
	public TokenMember(long type, long data, String string)
	{
		super(type, data, string);
	}
	
	@Override
	public Member parse(ClassData data)
	{
		int pos1 = string.indexOf("(");
		String name = string.substring(0, pos1 != -1 ? pos1 : string.length());
		String[] split = name.split("\\.");
		
		int count = split.length - 1;
		
		String memberName = "";
		String className = data.fullClassName;
		
		if (count == 0)
			memberName = name;
		else if (count == 1)
		{
			if ("this".equals(split[0]))
				memberName = split[1];
			else
			{
				className = split[0];
				memberName = split[1];
			}
		}
		else if (count > 1)
		{
			memberName = split[split.length - 1];
			className = CSString.unsplit(".", Arrays.copyOf(split, split.length - 1));
		}
		
		return data.getMember(className, memberName);
	}
}
