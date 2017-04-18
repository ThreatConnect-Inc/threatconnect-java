package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Group;

public class GroupBuilder extends AbstractGroupBuilder<GroupBuilder>
{
	public Group createGroup()
	{
		return new Group(id, name, type, owner, ownerName, dateAdded, webLink);
	}
}