package com.griddynamics.jagger.jenkins.plugin;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import org.kohsuke.stapler.DataBoundConstructor;


/**
 * Created by IntelliJ IDEA.
 * User: amikryukov
 * Date: 1/31/13
 */
public class AdditionalProperties implements Describable<AdditionalProperties>{

    private final boolean declared;
    private final String textFromArea;
    private String textFromAreaActual;

    @DataBoundConstructor
    public AdditionalProperties(boolean declared, String textFromArea) {

        this.declared = declared;
        this.textFromArea = textFromArea;
        this.textFromAreaActual = textFromArea;
    }

    public boolean isDeclared() {
        return declared;
    }

    public String getTextFromArea() {
        return textFromArea;
    }

    public Descriptor<AdditionalProperties> getDescriptor() {
        return Hudson.getInstance().getDescriptor(getClass());
    }

    public String getTextFromAreaActual() {
        return textFromAreaActual;
    }

    public void setTextFromAreaActual(String textFromAreaActual) {
        this.textFromAreaActual = textFromAreaActual;
    }


    @Extension
    public static class DescriptorAP extends Descriptor<AdditionalProperties>{

       @Override
       public String getDisplayName() {
           return "Additional Properties";
       }
    }


}
