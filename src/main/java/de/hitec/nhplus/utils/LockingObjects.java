package de.hitec.nhplus.utils;
import java.util.Calendar;
import java.util.List;

public abstract class LockingObjects
{
    protected abstract Calendar getCurrentDate();
    protected abstract boolean checkIfAboveLegalLockDate();
    protected abstract boolean isObjectLocked();
    protected abstract void archiveObject();
}
