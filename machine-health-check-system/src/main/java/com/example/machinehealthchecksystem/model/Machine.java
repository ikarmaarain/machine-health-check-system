package com.example.machinehealthchecksystem.model;

public class Machine {

    private String machineName;
    private String ipAddress;

    public Machine () {
    }

    public Machine(String machineName, String ipAddress) {
        this.machineName = machineName;
        this.ipAddress = ipAddress;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "machineName: " + machineName + "," +
                " ipAddress: " + ipAddress;
    }
}
