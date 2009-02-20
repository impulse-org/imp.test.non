package org.eclipse.imp.test.non;

import org.eclipse.imp.runtime.PluginBase;
import org.osgi.framework.BundleContext;

public class Activator extends PluginBase {

    public static final String kPluginID= "org.eclipse.imp.test.non";
    public static final String kLanguageID= "non";

    /**
     * The unique instance of this plugin class
     */
    protected static Activator sPlugin;

    public static Activator getInstance() {
        if (sPlugin == null) {
            new Activator();
        }
        return sPlugin;
    }

    public Activator() {
        super();
        sPlugin= this;
    }

    public void start(BundleContext context) throws Exception {
        super.start(context);

    }

    public String getID() {
        return kPluginID;
    }

    @Override
    public String getLanguageID() {
        return kLanguageID;
    }
}
