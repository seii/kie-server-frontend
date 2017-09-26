package org.wildfly.bpms.frontend.exception;

public class BpmsFrontendException extends Exception
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9114349942475869629L;

	public BpmsFrontendException()
    {
        super();
    }

    public BpmsFrontendException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public BpmsFrontendException( String message )
    {
        super( message );
    }

    public BpmsFrontendException( Throwable cause )
    {
        super( cause );
    }
}



