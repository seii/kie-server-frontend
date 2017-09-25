package org.wildfly.bpms.frontend.exception;

public class BPMSFrontendException extends Exception
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9114349942475869629L;

	public BPMSFrontendException()
    {
        super();
    }

    public BPMSFrontendException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public BPMSFrontendException( String message )
    {
        super( message );
    }

    public BPMSFrontendException( Throwable cause )
    {
        super( cause );
    }
}



