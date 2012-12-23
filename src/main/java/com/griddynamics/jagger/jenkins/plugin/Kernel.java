package com.griddynamics.jagger.jenkins.plugin;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Created with IntelliJ IDEA.
 * User: amikryukov
 * Date: 12/21/12
 */
public class Kernel extends Role implements Describable<Kernel> {


    @DataBoundConstructor
    public Kernel(){}

    public Descriptor<Kernel> getDescriptor() {
        return Hudson.getInstance().getDescriptor(getClass());
    }

    @Override
    public String toString() {
        return "KERNEL";
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<Kernel>{

        @Override
        public String getDisplayName() {
            return "KERNEL";
        }
    }

}
