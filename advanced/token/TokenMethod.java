package com.chaosdev.textmodloader.advanced.token;

import com.chaosdev.textmodloader.advanced.ClassData;
import com.chaosdev.textmodloader.advanced.members.Member;
import com.chaosdev.textmodloader.advanced.members.Method;

public class TokenMethod extends TokenMember
{
	public TokenMethod(String string)
	{
		super(Token.TYPE_METHOD, 0, string);
	}
	
	@Override
	public Method parse(ClassData data)
	{
		Member member = super.parse(data);
		return (Method) member;
	}
}
