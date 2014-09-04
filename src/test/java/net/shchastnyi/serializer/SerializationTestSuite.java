package net.shchastnyi.serializer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UtilsTest.class,
        NodeConstructionTest.class,
        NodesToBytesTest.class
})
public class SerializationTestSuite {}
