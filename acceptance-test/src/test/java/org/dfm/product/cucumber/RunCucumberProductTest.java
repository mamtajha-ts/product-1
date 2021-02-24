package org.dfm.product.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", strict = true, plugin = {
    "json:target/cucumber/product.json", "json:target/cucumber/product.xml"}, tags =
    "@Product", glue = "classpath:org.dfm.product.cucumber")
public class RunCucumberProductTest {

}
