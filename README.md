# Multithreaded_Downloader_Manager_Java

How to Use -:

```java
 Downloader downloader = new Downloader(URL_FILE,4);
 downloader.startDownload();
 ```
 
 Attach a listener-:
 ```java
   downloader.setDownloadStatusListener(new DownloadListener() {
            @Override
            public void update(long downloaded, int speed) {

        
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onPause(ArrayList<Long> downloaded) {
             
            }

            @Override
            public void onPartError(int code, String message, int partNo) {

            }

            @Override
            public void onError(String message) {
    
            }


            @Override
            public void onPartStatus(long downloaded, int partNo) {

            }

            @Override
            public void onPartCompleted(int partNo) {

            }


        });
 ```

        
