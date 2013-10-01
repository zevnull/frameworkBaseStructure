package com.autotests.data.primitives;

/**
 * ITextField interface which provides common methods for TextField element.
 * 
 * @author Andrei_Tsiarenia
 *
 */
public interface ITextField extends IElement 
{
    void type(String text);

    void clear();

    void clearAndType(String text);
}
