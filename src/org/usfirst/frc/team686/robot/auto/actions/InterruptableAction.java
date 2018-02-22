package org.usfirst.frc.team686.robot.auto.actions;

import org.usfirst.frc.team686.robot.lib.util.DataLogger;

/**
 * Composite action, running all sub-actions at the same time All actions are
 * started then updated until all actions report being done.
 * 
 * The entire set of actions will be declared done if
 * 		1: the entire list of actions report done
 * 		2: any of the interrupting actions report done 
 * 
 * @param A
 *            List of Action objects
 */
public class InterruptableAction implements Action 
{

	private final Action mInterruptingAction;
	private final Action mAction;
    
    public InterruptableAction(Action interruptingActions, Action actions) 
    {
    	mInterruptingAction = interruptingActions;
    	mAction = actions;
    }

    @Override
    public void start() 
    {
    	mInterruptingAction.start();
    	mAction.start();
    }
    
    @Override
    public void update() 
    {
    	mInterruptingAction.update();
    	mAction.update();
    }

    @Override
    public boolean isFinished() 
    {
    	boolean finished = (mInterruptingAction.isFinished()) || (mAction.isFinished()); 
        return finished;
    }

    @Override
    public void done() 
    {
    	mInterruptingAction.done();
    	mAction.done();
    }

	private final DataLogger logger = new DataLogger()
    {
        @Override
        public void log()
        {
        	mInterruptingAction.getLogger().log();
        	mAction.getLogger().log();
	    }
    };
	
    public DataLogger getLogger() { return logger; }
    
}
