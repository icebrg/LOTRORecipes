package net.gilstraps.lotro.recipetracker.model;

/**
 * I eschew runtime exceptions. But in this case, we're doing a one-shot batch process, so an error is a fail-and-stop
 * anyway.
 */
public class AlreadyRegisteredException extends RuntimeException {
    public AlreadyRegisteredException(Thing alreadyRegistered) {
        super("A " + alreadyRegistered.getClass().getSimpleName() + "' with name '" + alreadyRegistered.getName() + "' is already registered!" );
    }
}
