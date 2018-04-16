package no.karbowski.pingapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ping {

    /**
     * Pings the given host address once using a system call to "ping" and returns the latency in ms
     * Note: hostAddress is not validated, if not a valid IP address or hostname, IOException will
     * be thrown.
     * Note: "android.permission.INTERNET" must be declared in the manifest
     * Note: Doing such system calls have disadvantages
     *
     * @param  hostAddress  Host address in dotted IP address or hostname
     * @return              The latency in ms. Returns 0 if host not found or not replying.
     * @throws IOException  If system call fails (use getMessage() for details)
     */

    public static double ping(String hostAddress) throws IOException {
        int exit = -1;
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec("ping -c 1 " + hostAddress);
            process.waitFor();
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line + "\n";
            }
            bufferedReader.close();
            exit = process.exitValue();
            process.destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IOException("System call interrupted");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed reading system call result");
        }

        if(exit != 0) {
            throw new IOException("System call failed with exit: " + exit);
        }

        Pattern latencyPattern = Pattern.compile(".*?time=(.*)?ms");
        Matcher latencyMatcher = latencyPattern.matcher(result);
        double latency = 0;
        while (latencyMatcher.find()) {
            try {
                latency = Double.parseDouble(latencyMatcher.group(1));
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return latency;
    }
}
