package com.techweblearn;


import com.techweblearn.Utils.FetchDownloadFileInfo;

public class Main {
    private static final String CD_FNAME = "fname=";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    //Accept-Ranges: none // When No Partial Request
    public static void main(String[] args) {


        String URL_FILE="https://images.pexels.com/photos/1168981/pexels-photo-1168981.jpeg?cs=srgb&dl=adventure-daylight-desktop-wallpaper-1168981.jpg";

        FileDownloadInfo fileDownloadInfo=FetchDownloadFileInfo.getUrlFileInfo(URL_FILE);
        Downloader downloader=new Downloader();
        downloader.downloadFile(fileDownloadInfo, 4
                , new DownloadListener() {
            @Override
            public void onPreparedDownloading() {

            }

            @Override
            public void onStartDownload(DownloadingStatus downloadingStatus) {

            }

            @Override
            public void onErrorDownloading() {

            }

            @Override
            public void resumeDownloading() {

            }

            @Override
            public void update(long downloaded, int speed) {

            }

            @Override
            public void downloadSuccesfull() {

            }

            @Override
            public void combiningFiles() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void errorCombiningFiles() {

                System.out.println("Combining Error");

            }

                    @Override
                    public void combiningCompleted() {

                    }

                    @Override
                    public void deleteCompleted() {

                    }
                });
    }


}
