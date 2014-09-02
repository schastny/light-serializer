package net.shchastnyi.serializer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    //TODO Inherited fields
    //TODO private fields without getters
    //TODO transient mark
    //TODO BigEndian/LittleEndian

    //TODO default values
    //TODO recreate objects (constructors, setters)

    //TODO string encoding
    //TODO switch for primitives|arrays|enums|all_other_objects
}
