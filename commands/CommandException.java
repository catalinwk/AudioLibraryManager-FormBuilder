/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.commands;

/**
 * Used for catching audio commands exceptions
 * @author Catalin Mazilu
 */
public class CommandException extends Exception
{
    public CommandException (String message){
        super(message);
    }
}
