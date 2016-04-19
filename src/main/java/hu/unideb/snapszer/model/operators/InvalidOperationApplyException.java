package hu.unideb.snapszer.model.operators;

/**
 * Created by User on 2016. 04. 19..
 */
public class InvalidOperationApplyException extends Exception {

    public InvalidOperationApplyException(String operator)
    {
        super(String.format("The %s is not applicable!", operator));
    }
}
