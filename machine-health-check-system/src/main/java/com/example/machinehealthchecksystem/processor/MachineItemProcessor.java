package com.example.machinehealthchecksystem.processor;


import com.example.machinehealthchecksystem.model.Machine;
import org.springframework.batch.item.ItemProcessor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MachineItemProcessor implements ItemProcessor<Machine, Machine> {

    @Override
    public Machine process(final Machine machine) {
        System.out.println("\nMachine Name : " + machine.getMachineName() +
                "\nMachine IP : " + machine.getIpAddress());
        runSystemCommand("ping " + machine.getIpAddress() + " -n 1");
        return machine;
    }

    private void runSystemCommand(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String s = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((s = inputStream.readLine()) != null) {
                stringBuilder.append(s);
            }
            if (stringBuilder.indexOf("Request timed out.") != -1) {
                System.out.println("Status: Machine is Down");
            } else if (stringBuilder.indexOf("Destination host unreachable.") != -1) {
                System.out.println("Status: Machine is Unreachable.");
            } else {
                System.out.println("Status: Machine is Active");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
