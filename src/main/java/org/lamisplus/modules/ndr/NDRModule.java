package org.lamisplus.modules.ndr;

import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.context.configurer.ComponentScanConfigurer;

public class NDRModule  extends AcrossModule {
    public  static final String NAME = "NDRModule";

    public NDRModule() {
        super ();
        addApplicationContextConfigurer (new ComponentScanConfigurer (
                getClass ().getPackage ().getName () + ".repositories",
                getClass ().getPackage ().getName () + ".service",
                getClass ().getPackage ().getName () + ".controller"
        ));
    }

    @Override
    public String getName() {
        return NAME;
    }
}
