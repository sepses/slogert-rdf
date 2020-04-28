package org.sepses.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sepses.MainTransformer;

import java.io.File;
import java.io.FileNotFoundException;

public class MainTransformerTest {
    private static ClassLoader classLoader;

    @BeforeClass public static void setup() {
        classLoader = MainTransformerTest.class.getClassLoader();
        //File slogertTTL = new File(classLoader.getResource("slogert-owl.ttl").getFile());
    }

    @Test public void transformAuth() throws FileNotFoundException {
        File authTTL = new File(classLoader.getResource("auth.log_structured.ttl").getFile());

        String[] params = { "slogert-owl.ttl", authTTL.getAbsolutePath(), "output-model.ttl"};
        MainTransformer.main(params);
    }
}