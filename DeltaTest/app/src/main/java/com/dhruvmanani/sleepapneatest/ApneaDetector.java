package com.dhruvmanani.sleepapneatest;

import android.util.Log;

import java.io.File;

public class ApneaDetector {

    private int THRESHHOLD = 380;

    public String getStatus(String pathRaw) {
        String status = "N/A";
        try {
            WavFile wavFile = WavFile.openWavFile(new File(pathRaw + "_audio_record.wav"));
            wavFile.display();

            int[] list = new int[(int) wavFile.getNumFrames() * 2];

            wavFile.readFrames(list, (int) wavFile.getNumFrames());


            //System.out.println(hasApnea(list, (int) wavFile.getNumFrames(), (int) wavFile.getSampleRate()));

            status = this.hasApnea(list, (int) wavFile.getNumFrames(), (int) wavFile.getSampleRate());

            wavFile.close();

            return status;

        } catch (Exception e) {
            System.err.println(e);
        }

        return status;

    }

    private String hasApnea(int[] list, int frames, int sampleRate) {
        int episodes = 0;
        int delta = -1;
        for (int i = 0; i < frames; i += sampleRate) {
            if (Math.abs(list[i]) < THRESHHOLD) {
                delta++;
            } else {
                if (delta >= 10) episodes++;
                delta = -1;
            }
        }
        if (delta >= 10) episodes++;

        Log.e("The number of episodes is ", Integer.toString(episodes));
        //System.out.println("The number of episodes is " + episodes);

        double hours = frames/(3600.0*sampleRate);

        double apneicEpisodeRate = episodes/hours;

        if (apneicEpisodeRate < 5) {
            return "No sleep apnea";
        } else if (apneicEpisodeRate <= 15) {
            return "Mild sleep apnea";
        } else if (apneicEpisodeRate <= 30) {
            return "Moderate sleep apnea";
        } else {
            return "Severe sleep apnea";
        }
    }

}
