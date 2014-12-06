package com.example.WeatherTracker;

import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;

public class HTTPRequestAgent {

    public InputStream downloadURLBlocking(final String myURL) {
        try {
            return Executors.newSingleThreadExecutor().submit(new Callable<InputStream>() {
                @Override
                public InputStream call() throws Exception {
                    return HTTPRequestAgent.this.downloadURLHelper(myURL);
                }
            }).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void downloadURLAsync(String myURL,
                                 HTTPAgentCallBackHandler callBackHandler) {
        new DownloadWebPageTask(callBackHandler).execute(myURL);
    }

    private InputStream downloadURLHelper(String myURL) throws IOException {
        InputStream is = null;
        int maxLen = 102400; //100 KB Max Len;
        try {
            Log.d("ANOOPC", "URL=" + myURL);
            URL url = new URL(myURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();
            Log.d("ANOOPC", "response code=" + Integer.toString(responseCode));
            return connection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, InputStream> {
        private HTTPAgentCallBackHandler handler;

        public DownloadWebPageTask(HTTPAgentCallBackHandler handler) {
            super();
            this.handler = handler;
        }

        @Override
        protected InputStream doInBackground(String... params) {
            try {
                return downloadURLHelper(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(InputStream s) {
            if (this.handler != null) {
                this.handler.didReceiveResponse(s);
            }
        }
    }
}
