package com.chaosdev.textmodloader.advanced.token;

import com.chaosdev.textmodloader.advanced.ClassData;
import com.chaosdev.textmodloader.advanced.members.Field;
import com.chaosdev.textmodloader.advanced.members.Member;

public class TokenField extends TokenMember
{
	public TokenField(String string)
	{
		super(Token.TYPE_FIELD, 0, string);
	}
	
	@Override
	public Field parse(ClassData data)
	{
		Member member = super.parse(data);
		return (Field) member;
	}
}
