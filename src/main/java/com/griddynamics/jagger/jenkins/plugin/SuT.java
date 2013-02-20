package com.griddynamics.jagger.jenkins.plugin;


import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import hudson.util.FormValidation;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: amikryukov
 * Date: 12/21/12
 */


/**
 * To Make Object of Server that we want to test
 */
public class SuT implements Describable<SuT>, SshNode {

    private final String serverAddress;   //to view in Jenkins - could be $Parametr for example

    private String serverAddressActual;  //actual address

    private final String userName;

    private String userNameActual;

    private final String sshKeyPath ;

    private String sshKeyPathActual;

    private final boolean useJmx;

    private final String jmxPort;

    private String jmxPortActual;

    private final boolean setJavaHome;

    private final String javaHome;

    private String javaHomeActual;

    private final String javaOptions;

    private String javaOptionsActual;


    @DataBoundConstructor
    public SuT(String serverAddress, String userName, String sshKeyPath,
               String jmxPort, boolean useJmx, boolean setJavaHome, String javaHome, String minJavaHeap, String maxJavaHeap, boolean useAdvanced, String javaOptions){

        this.serverAddress = serverAddress;
        this.serverAddressActual = serverAddress;
        this.userName = userName;
        this.userNameActual = userName;
        this.sshKeyPath = sshKeyPath;
        this.sshKeyPathActual = sshKeyPath;
        this.jmxPort = jmxPort;
        this.useJmx = useJmx;
        this.setJavaHome = setJavaHome;
        this.javaHome = javaHome;
        this.javaOptions = javaOptions;

        setJavaHomeActual(javaHome);

        setJmxPortActual(jmxPort);
    }


    public void setJavaOptionsActual(String javaOptionsActual) {
        this.javaOptionsActual = javaOptionsActual;
    }

    public String getJavaOptions() {
        return javaOptions;
    }

    public String getJavaOptionsActual() {
        return javaOptionsActual;
    }

    public boolean isSetJavaHome() {
        return setJavaHome;
    }

    public String getJavaHome() {
        return javaHome;
    }

    public String getJavaHomeActual() {
        return javaHomeActual;
    }

    public void setJavaHomeActual(String javaHomeActual) {
        this.javaHomeActual = javaHomeActual;
    }

    public String getJmxPortActual() {
        return jmxPortActual;
    }

    public void setJmxPortActual(String jmxPortActual) {
        this.jmxPortActual = jmxPortActual;
    }

    public boolean isUseJmx() {
        return useJmx;
    }

    public String getJmxPort() {
        return jmxPort;
    }

    public String getUserNameActual() {
        return userNameActual;
    }

    public void setUserNameActual(String userNameActual) {
        this.userNameActual = userNameActual;
    }

    public String getSshKeyPathActual() {
        return sshKeyPathActual;
    }

    public void setSshKeyPathActual(String sshKeyPathActual) {
        this.sshKeyPathActual = sshKeyPathActual;
    }

    public String getServerAddressActual() {
        return serverAddressActual;
    }


    public String getUserName() {
        return userName;
    }

    public String getSshKeyPath() {
        return sshKeyPath;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public Descriptor<SuT> getDescriptor() {

        return Hudson.getInstance().getDescriptor(getClass());
    }

    public void setServerAddressActual(String s) {
        serverAddressActual = s;
    }

    @Extension
    public static class DescriptorNTA extends Descriptor<SuT>{

        @Override
        public String getDisplayName() {
            return "SuT";
        }

        /**
         * For test Server Address if it available
         * @param value String from Server Address form
         * @param installAgent then we can test ssh port 22
         * @return OK if ping, ERROR otherwise
         */
        public FormValidation doCheckServerAddress(@QueryParameter String value,@QueryParameter("installAgent") boolean installAgent) {

            try {

                if(value == null || value.matches("\\s*")) {
                    return FormValidation.warning("Set Address");
                }

                if(value.startsWith("$")){
                    return FormValidation.ok();
                }

                if(installAgent){
                    new Socket(value,22).close();
                } else {

                    String cmd = "";
                    if(System.getProperty("os.name").startsWith("windows")){
                        cmd = "ping -n 1 " + value;
                    } else {
                        cmd = "ping -c 1 " + value;
                    }
                    Process p1 = java.lang.Runtime.getRuntime().exec(cmd);
                    if(p1.waitFor() == 0) {
                        return FormValidation.ok();
                    } else {
                        return FormValidation.error("Server unreachable");
                    }
                }
            } catch (UnknownHostException e) {
                return FormValidation.error("Unknown Host\t"+value+"\t"+e.getLocalizedMessage());
            } catch (IOException e) {
                return FormValidation.error("Can't Reach Host on 22 port");
            } catch (InterruptedException e) {
                return FormValidation.error("Interrapted Exception");
            }
            return FormValidation.ok();
        }

         /**
         * testing if jmx ports given correctly
         * @param value jmx port(s)
         * @return FormValidation
         */
        public FormValidation doCheckJmxPort(@QueryParameter String value) {

            if(value == null || value.matches("\\s*")) {
                return FormValidation.warning("Set JMX Port(s)");
            } else if (!value.matches("\\d+(,\\d+)*")) {
                return FormValidation.error("wrong format: split with comas");
            } else {
                return FormValidation.ok();
            }
        }
    }
}