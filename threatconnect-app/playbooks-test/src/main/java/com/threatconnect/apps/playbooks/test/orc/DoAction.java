package com.threatconnect.apps.playbooks.test.orc;

import java.util.List;

/**
 * @author Greg Marut
 */
public class DoAction extends AbstractThen<PlaybooksOrchestration>
{
	//holds the list of runnable actions
	private final List<PlaybooksAction> actions;
	
	DoAction(final PlaybooksOrchestration then, final List<PlaybooksAction> actions)
	{
		super(then);
		this.actions = actions;
	}
	
	/**
	 * Executes a runnable action
	 * @param runnable
	 * @return
	 */
	public DoAction execute(final PlaybooksAction runnable)
	{
		actions.add(runnable);
		return this;
	}
}
