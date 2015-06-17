package com.threatconnect.sdk.app;

/**
 * Created by dtineo on 5/15/15.
 */
public enum ExitStatus
{

    Success("Success", 0), Failure("Failure", 1), Partial_Failure("Partial Failure", 3);

    private final String status;
    private final int exitCode;

    ExitStatus(String status, int exitCode)
    {
        this.status = status;
        this.exitCode = exitCode;
    }

    public String getStatus()
    {
        return status;
    }

    public int getExitCode()
    {
        return exitCode;
    }
}
