package org.lamisplus.modules.ndr;

import com.foreach.across.config.AcrossApplication;
import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.context.configurer.ComponentScanConfigurer;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import org.lamisplus.modules.hiv.HivModule;
import org.lamisplus.modules.patient.PatientModule;
import org.lamisplus.modules.triage.TriageModule;

@AcrossApplication(
        modules = {
                AcrossHibernateJpaModule.NAME,
                PatientModule.NAME,
                HivModule.NAME,
                TriageModule.NAME
        })
public class NDRModule  extends AcrossModule {
    public  static final String NAME = "NDRModule";

    public NDRModule() {
        super ();
        addApplicationContextConfigurer (new ComponentScanConfigurer (
                getClass ().getPackage ().getName () + ".repositories",
                getClass ().getPackage ().getName () + ".service",
                getClass ().getPackage ().getName () + ".mapper",
                getClass ().getPackage ().getName () + ".controller"
        ));
    }

    @Override
    public String getName() {
        return NAME;
    }
}
