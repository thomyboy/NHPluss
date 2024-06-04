package de.hitec.nhplus.utils;
import java.util.Calendar;
import java.util.List;

/**
 * Abstract class defining methods for locking and archiving objects.
 * Subclasses must implement these methods to provide specific locking and archiving logic.
 */
public abstract class LockingObjects
{
    /**
     * Retrieves the current date as a Calendar object.
     *
     * @return The current date as a Calendar object.
     */
    protected abstract Calendar getCurrentDate();

    /**
     * Checks if the current date is above the legal lock date.
     *
     * @return true if the current date is above the legal lock date, false otherwise.
     */
    protected abstract boolean checkIfAboveLegalLockDate();

    /**
     * Checks if the object is locked.
     *
     * @return true if the object is locked, false otherwise.
     */
    protected abstract boolean isObjectLocked();

    /**
     * Archives the object.
     * This method is called when an object needs to be archived after locking.
     */
    protected abstract void archiveObject();
}