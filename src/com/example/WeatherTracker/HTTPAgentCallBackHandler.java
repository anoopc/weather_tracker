package com.example.WeatherTracker;

import java.io.InputStream;

/**
 * Created by anoopc on 12/2/14.
 */

public interface HTTPAgentCallBackHandler {
    public void didReceiveResponse(InputStream responseStream);
}
