/**
 * mirror was made by Jadon Fowler. This file is licensed under the MIT License.
 */
package com.perceivedev.uskyfix.mirror;

/**
 * @author Jadon "Phase" Fowler on Feb 16, 2015
 */
public class Mirror {

    public static <T> ReflectedClass<T> $(T o) {
        return new ReflectedClass<T>(o);
    }

}
