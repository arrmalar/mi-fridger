package com.meefri.app.threads;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.Command;

public class VerificationThread extends Thread {
    @Override
    public void run() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

